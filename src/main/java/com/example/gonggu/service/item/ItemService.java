package com.example.gonggu.service.item;

import com.example.gonggu.domain.user.ListOfParticipantForUser;
import com.example.gonggu.dto.item.ItemJoinAcceptJson;
import com.example.gonggu.domain.category.Category;
import com.example.gonggu.domain.item.*;
import com.example.gonggu.domain.user.User;
import com.example.gonggu.dto.item.*;
import com.example.gonggu.dto.user.NotiContents;
import com.example.gonggu.exception.BadRequestException;
import com.example.gonggu.exception.BaseException;
import com.example.gonggu.exception.NotFoundException;
import com.example.gonggu.persistence.category.CategoryRepository;
import com.example.gonggu.persistence.item.ItemImgPathRepository;
import com.example.gonggu.persistence.item.ItemRepository;
import com.example.gonggu.persistence.item.ListOfLikeForItemRepo;
import com.example.gonggu.persistence.item.ListOfParticipantForItemRepo;
import com.example.gonggu.persistence.user.UserRepository;
import com.example.gonggu.service.user.NotiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ListOfLikeForItemRepo likeRepo;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemImgPathRepository itemImgRepo;
    @Autowired
    private ListOfParticipantForItemRepo participantListRepo;

    @Autowired
    private NotiService notiService;

    // like 관련 service
    // acceptJson
    //      A_itemId , UserLikeEmail 필수
    // return
    //      true : like 추가
    //      false : like 제거
    @Transactional
    public void toggleLike(ItemLikeJson acceptJson){
        Item item = itemRepository.getOne(Long.parseLong(acceptJson.getItemId()));
        boolean result = true;
        Integer likeNum = item.getNumOfLike();
        for(ListOfLikeForItem temp :item.getLikeForItemList())
            if(temp.getUserEmail().equals(acceptJson.getUserEmail()) ){
                item.getLikeForItemList().remove(temp);
                likeRepo.delete(temp.getLikeId());
                result = false; break;
            }

        if(result) {
            ListOfLikeForItem newlike = new ListOfLikeForItem();
            newlike.setUserEmail(acceptJson.getUserEmail());
            item.getLikeForItemList().add(newlike);
            item.setNumOfLike(likeNum + 1);
        }

    }

    /*
    * 공구 item 생성관련 Service
    * acceptJson
    *      A_TabNumber, Category, ItemTitle, ItemAmountLimit, a_imgPathList
    *      OneContents, OneLocation, OneEndDate, ""OneImgPath"" <- 이미지 여러개 들어오는 것 처리해야함
    * return
    *      true : create item success
    *      false : create item fail
    *  */
    public void createItem(ItemCreateJson acceptJson) {
        Item newItem = new Item();
        ItemTab1 itemTab1 = new ItemTab1();
        ItemTab2 itemTab2 = new ItemTab2();
        ItemTab3 itemTab3 = new ItemTab3();
        ItemTab4 itemTab4 = new ItemTab4();
        ItemTab5 itemTab5 = new ItemTab5();
        ItemImgPath itemImgPath;
        List<ItemImgPath> imgPathList = new ArrayList<>();

        newItem.setNowTab(1);

        Category category = categoryRepository.findOne(Long.parseLong(acceptJson.getItemCategoryId()));
        if(category == null) throw new NotFoundException("카테고리가 존재하지 않습니다");
        newItem.setCategory(category);
        newItem.setTitle(acceptJson.getItemTitle());

        User writer = userRepository.getOne(Long.parseLong(acceptJson.getUserId()));
        if(writer == null) throw new NotFoundException("유저가 존재하지 않습니다");
        newItem.setUser(writer);
        newItem.setAmountLimit(Integer.valueOf(acceptJson.getItemAmountLimit()));
        newItem.setNumOfLike(0);
        newItem.setNumOfOrder(0);
        newItem.setIsDeleted(false);

        newItem.setThumnail(acceptJson.getImgPathList()[0]); // 제일 처음에 있는 사진으로 대표이미지 설정

        // 이미지 추가하기
        for (String img : acceptJson.getImgPathList()) {
            itemImgPath = new ItemImgPath();
            itemImgPath.setTab(1);
            itemImgPath.setImg_path(img);
            imgPathList.add(itemImgPath);
        }
        newItem.setImgPaths(imgPathList); // 이미지리스트 추가하기

        // 공구 item tab1 설정
        itemTab1.setContents(acceptJson.getTabOne().getContents());
        itemTab1.setLocation(acceptJson.getTabOne().getLocation());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // tab1에서 년월일만 입력받아서 이렇게 설정함
        try {
            Date insertDate = sdf.parse(acceptJson.getTabOne().getEndDate());
            itemTab1.setEndDate(insertDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newItem.setItemTab1(itemTab1);

        // 공구 item tab2,3,4,5 null 값으로 생성
        newItem.setItemTab2(itemTab2);
        newItem.setItemTab3(itemTab3);
        newItem.setItemTab4(itemTab4);
        newItem.setItemTab5(itemTab5);


        // 공구 owner
        ListOfParticipantForUser ownersItemInfo = new ListOfParticipantForUser();
        List<ListOfParticipantForUser> participantsListItem = new ArrayList<>();

        ownersItemInfo.setOwner(true);
        writer.getParticipants().add(ownersItemInfo);
        participantsListItem.add(ownersItemInfo);
        newItem.setListOfParticipantForUser(participantsListItem);

        itemRepository.save(newItem);
    }

    /*
    * 공구 tab1에서 아이템 삭제 service
    * parameter
    *   itemId
    * return
    *   true : delete success
    *   false : delete fail
    * */
    public void deleteItem(String itemId) {
        Item item = itemRepository.findOne(Long.parseLong(itemId));
        if(item == null) throw new NotFoundException("존재하지 않은 아이템입니다");
        if(item.getNowTab() != 1) throw new BadRequestException("현재 탭 단계에서는 삭제가 불가능합니다");
        else item.setIsDeleted(true);
        itemRepository.save(item);
    }

    /*
    * 공구 item에 대한 모든 정보 반환하는 service
    * parameter
    *   itemId
    * return
    *   itemInfoJson
    * */
    public ItemInfo getItemDetail(String itemId) {
        ItemInfo infoJson = new ItemInfo();
        Item item = itemRepository.findOne(Long.parseLong(itemId));

        if(!item.getIsDeleted()) { // 삭제되지 않은 경우
            infoJson.setItemId(item.getItemId().toString());
            infoJson.setWriterId(item.getUser().getUserId().toString());
            infoJson.setWriterUserName(item.getUser().getUserName());
            infoJson.setItemTitle(item.getTitle());
            infoJson.setCategory(item.getCategory().getCategory());
            infoJson.setNowTab(item.getNowTab());
            infoJson.setNumOfLike(item.getNumOfLike());
            switch (item.getNowTab()) {
                case 5:
                    ItemTab5Json infoTab5 = new ItemTab5Json();
                    infoTab5.setContents(item.getItemTab5().getContents());
                    infoTab5.setLocationDetail(item.getItemTab5().getLocationDetail());
                    infoJson.setTabFive(infoTab5);
                case 4:
                    ItemTab4Json infoTab4 = new ItemTab4Json();
                    infoTab4.setContents(item.getItemTab4().getContents());
                    infoTab4.setArrivedTime(item.getItemTab4().getArrivedTime().toString());
                    infoJson.setTabFour(infoTab4);
                case 3:
                    ItemTab3Json infoTab3 = new ItemTab3Json();
                    infoTab3.setContents(item.getItemTab3().getContents());
                    infoJson.setTabThree(infoTab3);
                case 2:
                    ItemTab2Json infoTab2 = new ItemTab2Json();
                    infoTab2.setContents(item.getItemTab2().getContents());
                    infoTab2.setEndDate(item.getItemTab2().getEndDate().toString());
                    infoTab2.setOptionString(item.getItemTab2().getOptionString());
                    infoJson.setNumOfParticipant(item.getNumOfOrder());
                    Integer percentage = (item.getNumOfOrder()/item.getAmountLimit())*100;
                    infoJson.setParticipantPercent(percentage);
                    infoJson.setTabTwo(infoTab2);
                default:
                    ItemTab1Json infoTab1 = new ItemTab1Json();
                    infoTab1.setContents(item.getItemTab1().getContents());
                    infoTab1.setEndDate(item.getItemTab1().getEndDate().toString());
                    infoTab1.setLocation(item.getItemTab1().getLocation());
                    infoJson.setTabOne(infoTab1);
            }
            infoJson.setImgPathList(item.getImgPaths());
        }
        else infoJson.setIsDeleted(item.getIsDeleted()); // item이 삭제된 경우 삭제되었다는 값만 전달

        return infoJson;
    }

    /*
     * 공구 tab의 내용을 수정할때의 service
     * editTab == false -> 아이템이 다음 프로세스로 진행
     * acceptJson
     *   itemId, a_editTab, 변동된 tab의 내용
     * */
    @Transactional
    public void patchItemTab(ItemPatchJson acceptJson) {
        Item parentsItem = itemRepository.getOne(Long.parseLong(acceptJson.getItemId()));
        this.changeTabImgs(Integer.valueOf(acceptJson.getTargetTab()) , acceptJson.getImgPathList() ,parentsItem);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        switch (acceptJson.getTargetTab().toString()) {
            case "1" :
                ItemTab1 tab1 = parentsItem.getItemTab1();
                if(acceptJson.getEditTab()) {
                    parentsItem.setTitle(acceptJson.getItemTitle());
                    parentsItem.setAmountLimit(acceptJson.getItemAmountLimit());
                    Category category = categoryRepository.findByCategory(acceptJson.getCategory());
                    if(category == null) throw new NotFoundException("존재하지 않는 카테고리입니다");
                    parentsItem.setCategory(category);
                    if(acceptJson.getTabOne().getContents() != null) tab1.setContents(acceptJson.getTabOne().getContents());
                    if(acceptJson.getTabOne().getLocation() != null) tab1.setLocation(acceptJson.getTabOne().getLocation());
                    if(acceptJson.getTabOne().getEndDate() != null) {
                        try {
                            Date newEndDate = format.parse(acceptJson.getTabOne().getEndDate());
                            tab1.setEndDate(newEndDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    parentsItem.setItemTab1(tab1);
                }
                break;
            case "2" :
                ItemTab2 tab2 = parentsItem.getItemTab2();
                if(acceptJson.getTabTwo().getContents() != null) tab2.setContents(acceptJson.getTabTwo().getContents());
                if(acceptJson.getTabTwo().getOptionString() != null) tab2.setOptionString(acceptJson.getTabTwo().getOptionString());
                if(acceptJson.getTabTwo().getPrice() != null) tab2.setPrice(Integer.parseInt(acceptJson.getTabTwo().getPrice()));
                if(acceptJson.getTabTwo().getEndDate() != null) {
                    try {
                        Date twoEndDate = format.parse(acceptJson.getTabTwo().getEndDate());
                        tab2.setEndDate(twoEndDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                parentsItem.setItemTab2(tab2);
                break;
            case "3" :
                ItemTab3 tab3 = parentsItem.getItemTab3();
                if(acceptJson.getTabThree().getContents() != null) tab3.setContents(acceptJson.getTabThree().getContents());
                //CHECK : 여기서 에디트탭을 확인하는 이유는...? -> 수정이 아닌 처음 생성할 때의 설정
                if(!acceptJson.getEditTab()){


                    String optionListStr = this.returnOptionArrayStr(parentsItem.getItemTab2().getOptionString());
                    tab3.setOptionArray(optionListStr);
                    String[] opNamingArr = optionListStr.split(" ");
                    int[] optionCountArray = new int[opNamingArr.length];
                    int pricePerProduct = parentsItem.getItemTab2().getPrice();
                    int totalPrice = 0;

                    // 각 유저에 대해서 함수를 반복함
                    parentsItem.getParticipantForItemList().forEach(list->{
                        this.getOptionCountArray(list.getOptionString(),opNamingArr,optionCountArray);
                    });
                    tab3.setOptionCountArray(Arrays.toString(optionCountArray).replace("[","").replace("]",""));

                    for(int howmany : optionCountArray)
                        totalPrice += howmany*pricePerProduct;

                    tab3.setTotalPrice(totalPrice);

                }
                break;
            case "4" :
                ItemTab4 tab4 = parentsItem.getItemTab4();
                if(acceptJson.getTabFour().getArrivedTime() != null) {
                    try {
                        Date newArriveDate = format.parse(acceptJson.getTabFour().getArrivedTime());
                        tab4.setArrivedTime(newArriveDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(acceptJson.getTabFour().getContents() != null) tab4.setContents(acceptJson.getTabFour().getContents());
                parentsItem.setItemTab4(tab4);
                break;
            case "5" :
                ItemTab5 tab5 = parentsItem.getItemTab5();
                if(acceptJson.getTabFive().getLocationDetail() != null) tab5.setLocationDetail(acceptJson.getTabFive().getLocationDetail());
                if(acceptJson.getTabFive().getContents() != null) tab5.setContents(acceptJson.getTabFive().getContents());
                parentsItem.setItemTab5(tab5);
                break;
        }

        if(!acceptJson.getEditTab()) {
            parentsItem.setNowTab(Integer.valueOf(acceptJson.getTargetTab()));

            if(acceptJson.getTargetTab() >=3){
                parentsItem.getParticipantForItemList().forEach(list->{
                    NotiContents tempNoti = new NotiContents(
                            acceptJson.getTargetTab(), userRepository.findByUserName(list.getUserName()),
                            parentsItem.getItemId(), parentsItem.getTitle()
                    );
                    notiService.sendNoti(tempNoti);
                });
            }else if(acceptJson.getTargetTab() == 2 ){
                parentsItem.getLikeForItemList().forEach(likeInfo->{
                    NotiContents tempNoti = new NotiContents(
                            2, userRepository.findByUserEmail(likeInfo.getUserEmail()), parentsItem.getItemId(), parentsItem.getTitle()
                    );
                    notiService.sendNoti(tempNoti);
                });
            }
        }
        itemRepository.save(parentsItem);
    }

    /*
     * img list change
     * parameter
     *   tab , new_imgpaths , target Item
     * return
     *   void
     * */
    public void changeTabImgs(Integer tab,String[] imgpaths,Item target){

        List<ItemImgPath> list = target.getImgPaths().stream().filter(img->{
            if (img.getTab() == tab) {
                itemImgRepo.delete(img.getItemImgPathId());
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        for (String newImgString : imgpaths) {
            ItemImgPath newImgPath = new ItemImgPath();
            newImgPath.setTab(tab);
            newImgPath.setImg_path(newImgString);
            list.add(newImgPath);
        }

        target.setImgPaths(list);
    }

    /*
     * optionString 변환 Version2
     * */
    public String returnOptionArrayStr(String optionString){
        StringBuilder sb = new StringBuilder();

        String eachOptionPart[] = optionString.split("/");
        List optionArray = new ArrayList();

        for(String opstr : eachOptionPart){
            String temp[] = opstr.split(">");
            optionArray.add(temp[1]);
        }
        int opArrSize = optionArray.size();
        if(opArrSize >= 4) throw new BadRequestException("옵션은 4개 이상의 주제를 가질 수 없습니다.");

        String[] opArr_1 = optionArray.get(0).toString().split(",");
        String[] opArr_2;
        String[] opArr_3;

        String parentStr;
        String grParentStr;

        switch (opArrSize){
            case 1:
                for(int i=0;i<opArr_1.length ;i++){
                    sb.append(opArr_1[i]);
                    if(i != opArr_1.length-1) sb.append(" ");
                }
                break;
            case 2:
                opArr_2 = optionArray.get(1).toString().split(",");
                for(int i=0;i<opArr_1.length;i++) {
                    parentStr = opArr_1[i];
                    for (int p = 0; p < opArr_2.length; p++) {
                        sb.append(parentStr);
                        sb.append(",");
                        sb.append(opArr_2[p]);
                        sb.append(" ");
                    }
                }
                break;
            case 3:
                opArr_2 = optionArray.get(1).toString().split(",");
                opArr_3 = optionArray.get(2).toString().split(",");
                for(int i=0;i<opArr_1.length;i++){
                    grParentStr = opArr_1[i];
                    for(int p=0;p<opArr_2.length;p++){
                        parentStr = grParentStr + "," + opArr_2[p];
                        for(int k=0;k<opArr_3.length;k++){
                            sb.append(parentStr);
                            sb.append(",");
                            sb.append(opArr_3[k]);
                            sb.append(" ");
                        }
                    }
                }
                break;
            default:
                break;
        }

        return sb.toString().trim();

    }
    /*
    * 사용자 optionString 기반으로 optionCountArray 변환
    * */
    public void getOptionCountArray(String personalOption , String[] opNamingArr , int[] countArray){
        // Ex ) a,b>3/a,g>5
        String personalOptArr[] = personalOption.split("/");
        for(int p = 0;p<personalOptArr.length;p++){
            String personalOpt[] = personalOptArr[p].split(">");
            for(int i=0;i<opNamingArr.length;i++)
                if(opNamingArr[i].equals(personalOpt[0]))
                    countArray[i] += Integer.parseInt(personalOpt[1]);
        }
    }

    /*
     * 사용자가 공구 구매시 option에 대한 정보 전달 service
     * parameter
     *   itemId
     * return
     *   String : option에 대한 값 전달
     * */
    public String getOptionInfo(String itemId) {
        String result;
        Item parentItem = itemRepository.getOne(Long.parseLong(itemId));

        result = parentItem.getItemTab2().getOptionString();

        return result;
    }

    /*
     * 유저의 공구 참여
     * parameter
     *   acceptJson
     * */
    public void insertParticipantUser(ItemJoinAcceptJson acceptJson) {
        Item parentItem = itemRepository.getOne(Long.parseLong(acceptJson.getItemId()));
        if(parentItem == null) throw new NotFoundException("존재하지 않는 아이템입니다");
        ListOfParticipantForItem join = new ListOfParticipantForItem();
        join.setUserName(acceptJson.getUserName());
        join.setAccountBank(acceptJson.getAccountBank());
        join.setAccountHolder(acceptJson.getAccountHolder());
        join.setAccountNum(acceptJson.getAccountNum());
        join.setOptionString(acceptJson.getOptionString());
        join.setPrice(acceptJson.getPrice());
        join.setAmount(acceptJson.getAmount());
        List<ListOfParticipantForItem> list = parentItem.getParticipantForItemList();
        list.add(join);
        parentItem.setParticipantForItemList(list);

        // 공구 참여 리스트
        ListOfParticipantForUser tempParticipantForUser = new ListOfParticipantForUser();
        tempParticipantForUser.setOwner(false);
        User tempUser = userRepository.findByUserName(acceptJson.getUserName());
        if(tempUser == null) throw new NotFoundException("존재하지 않는 사용자입니다");
        tempUser.getParticipants().add(tempParticipantForUser);


        List<ListOfParticipantForUser> participantListForUser = parentItem.getListOfParticipantForUser();
        participantListForUser.add(tempParticipantForUser);
        parentItem.setListOfParticipantForUser(participantListForUser);
//        parentItem.getListOfParticipantForUser().add(tempParticipantForUser);

        itemRepository.save(parentItem);
    }

    /*
    * 현재 참여한 유저의 리스트를 확인
    * parameter
    *   itemId
    * return
    *   List user
    * */
    public List<ListOfParticipantForItem> getParticipantUser(String itemId){
        Item parentItem = itemRepository.getOne(Long.parseLong(itemId));
        return parentItem.getParticipantForItemList();
    }

    /*
     * 참여한 유저의 상태를 변경
     * parameter
     *   acceptJson
     * return
     *   void
     * */
    public void changeUserPermission(ParticipantListUserPermission acceptJson){
        ListOfParticipantForItem item = participantListRepo.findOne(Long.parseLong(acceptJson.getListItemId()));
        if(item == null) throw new NotFoundException("아이템에 참여한 유저의 정보를 찾을 수 없습니다");
        item.setUserPermission(acceptJson.getPermission());
        participantListRepo.save(item);
    }
}



/*
 * optionString 변환 -> 가격 O
 * */
//    public List returnOptionArrayStr(String optionString){
//        String[] optionsWrapper =optionString.split("/");
//        List<String> list = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//        String[] firstOptionAndPrice;
//        String[] secondOptionAndPrice;
//        String result;
//        switch (optionsWrapper.length){
//            case 1:
//                String[] optionAndPrice = optionString.split(">")[1].split(",");
//                for(String str : optionAndPrice){
//                    list.add(str.split(":")[0]);
//                }
//                break;
//            case 2:
//                firstOptionAndPrice = optionsWrapper[0].split(">")[1].split(",");
//                List<String> secondList = Arrays.asList(optionsWrapper[1].split(">")[1].split(","));
//                Collections.reverse(secondList);
//                secondOptionAndPrice = secondList.toArray(new String[secondList.size()]);
//
//                for(int f = 0;f<firstOptionAndPrice.length;f++){
//                    String parentStr = firstOptionAndPrice[f].split(":")[0];
//                    for(int s=0;s<secondOptionAndPrice.length;s++){
//                        sb.append(parentStr);
//                        sb.append(" ");
//                        sb.append(secondOptionAndPrice[s].split(":")[0]);
//                        list.add(f*secondOptionAndPrice.length,sb.toString());
//                        sb.delete(0,sb.length());
//                    }
//                }
//
//                break;
//            case 3:
//                firstOptionAndPrice = optionsWrapper[0].split(">")[1].split(",");
//
//                List<String> optionList = Arrays.asList(optionsWrapper[1].split(">")[1].split(","));
//                Collections.reverse(optionList);
//                secondOptionAndPrice = optionList.toArray(new String[optionList.size()]);
//
//                optionList = Arrays.asList(optionsWrapper[2].split(">")[1].split(","));
//                Collections.reverse(optionList);
//                String[] thirdOptionAndPrice = optionList.toArray(new String[optionList.size()]);
//
//                for(int f = 0;f<firstOptionAndPrice.length;f++){
//                    String firstStr = firstOptionAndPrice[f].split(":")[0];
//                    for(int s=0;s<secondOptionAndPrice.length;s++){
//                        String secondStr = secondOptionAndPrice[s].split(":")[0];
//                        for(int t=0;t<thirdOptionAndPrice.length;t++){
//                            sb.append(firstStr); sb.append(" ");
//                            sb.append(secondStr); sb.append(" ");
//                            sb.append(thirdOptionAndPrice[t].split(":")[0]);
//                            list.add(f*secondOptionAndPrice.length*thirdOptionAndPrice.length + s*thirdOptionAndPrice.length , sb.toString().trim());
//                            sb.delete(0,sb.length());
//                        }
//                    }
//
//                }
//                break;
//            default:
//                result = "check option String Length";
//                break;
//        }
//
//        return list;
////        return Arrays.toString(list.toArray()).replace("[","").replace("]","");
//    }