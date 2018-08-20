package com.example.gonggu.service.item;

import com.example.gonggu.controller.item.ItemAcceptJson;
import com.example.gonggu.controller.item.JoinAcceptJson;
import com.example.gonggu.domain.category.Category;
import com.example.gonggu.domain.item.*;
import com.example.gonggu.domain.user.User;
import com.example.gonggu.persistence.category.CategoryRepository;
import com.example.gonggu.persistence.item.ItemRepository;
import com.example.gonggu.persistence.item.ListOfLikeForItemRepo;
import com.example.gonggu.persistence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    // like 관련 service
    // acceptJson
    //      A_itemId , UserLikeEmail 필수
    // return
    //      true : like 추가
    //      false : like 제거
    @Transactional
    public boolean toggleLike(ItemAcceptJson acceptJson){
        Item item = itemRepository.getOne(Long.parseLong(acceptJson.getA_itemId()));
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


        return result;
    }

    /*
    * 공구 item 생성관련 Service
    * acceptJson
    *      A_TabNumber, Category, ItemTitle, ItemUserEmail, ItemAmountLimit,
    *      OneContents, OneLocation, OneEndDate, ""OneImgPath"" <- 이미지 여러개 들어오는 것 처리해야함
    * return
    *      true : create item success
    *      false : create item fail
    *  */
    public Boolean createItem(ItemAcceptJson acceptJson){
//    public Map<String,Object> createItem(ItemAcceptJson acceptJson){
//        Map<String, Object> results = new HashMap<>();
        Boolean result = true;
        Item item = new Item();
        ItemTab1 itemTab1 = new ItemTab1();
        ItemTab2 itemTab2 = new ItemTab2();
        ItemTab3 itemTab3 = new ItemTab3();
        ItemTab4 itemTab4 = new ItemTab4();
        ItemTab5 itemTab5 = new ItemTab5();
        ItemImgPath itemImgPath;
        List<ItemImgPath> imgPathList = new ArrayList<>();

        // 공구 item에 대한 기본 설정
        item.setNowTab(Integer.valueOf(acceptJson.getA_TabNumber()));
        Category getCategory = categoryRepository.findByCategory(acceptJson.getItemCategory());
        item.setCategory(getCategory);
        item.setTitle(acceptJson.getItemTitle());
        User getUser = userRepository.getOne(Long.parseLong(acceptJson.getA_userId()));
        item.setUser(getUser);
        item.setAmountLimit(Integer.valueOf(acceptJson.getItemAmountLimit())); // item의 최소공구수량 설정
        item.setNumOfLike(0);
        item.setNumOfOrder(0);
        item.setIsDeleted(false);

        item.setThumnail(acceptJson.getA_imgPathList()[0]); // 제일 처음에 있는 사진으로 대표이미지 설정

        // 이미지 추가하기
        for (String img : acceptJson.getA_imgPathList()) {
            itemImgPath = new ItemImgPath();
            itemImgPath.setTab(1);
            itemImgPath.setImg_path(img);
            imgPathList.add(itemImgPath);
        }
        item.setImgPaths(imgPathList); // 이미지리스트 추가하기

        // 공구 item tab1 설정
        itemTab1.setContents(acceptJson.getOneContents());
        itemTab1.setLocation(acceptJson.getOneLocation());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // tab1에서 년월일만 입력받아서 이렇게 설정함
        try {
            Date insertDate = sdf.parse(acceptJson.getOneEndDate());
            itemTab1.setEndDate(insertDate);
        } catch (ParseException e) {
            result = false;
            e.printStackTrace();
        }
        item.setItemTab1(itemTab1);

        // 공구 item tab2,3,4,5 null 값으로 생성
        item.setItemTab2(itemTab2);
        item.setItemTab3(itemTab3);
        item.setItemTab4(itemTab4);
        item.setItemTab5(itemTab5);

        itemRepository.save(item);
//        return results;
        return result;
    }

    /*
    * 공구 tab1에서 아이템 삭제 service
    * parameter
    *   itemId
    * return
    *   true : delete success
    *   false : delete fail
    * */
    public Boolean deleteItem(String itemId) {
        Boolean result = true;

        Item item = itemRepository.findOne(Long.parseLong(itemId));
        if (item.getNowTab() != 1) result = false; // 현재상태가 tab 1이 아닐 경우 삭제불가능하게 처리
        else item.setIsDeleted(true);
        itemRepository.save(item);

        return result;
    }

    /*
    * 공구 item에 대한 모든 정보 반환하는 service
    * parameter
    *   itemId
    * return
    *   itemInfoJson
    * */
    public ItemInfoJson getItemDetail(String itemId) {
        ItemInfoJson infoJson = new ItemInfoJson();
        Item item = itemRepository.findOne(Long.parseLong(itemId));


        if(!item.getIsDeleted()) { // 삭제되지 않은 경우
            infoJson.setItemId(item.getItemId());
            infoJson.setWriterId(item.getUser().getUserId());
            infoJson.setItemTitle(item.getTitle());
            infoJson.setCategory(item.getCategory().getCategory());
            infoJson.setNowTab(item.getNowTab());
            infoJson.setNumOfLike(item.getNumOfLike());
            Integer percentage = (item.getNumOfOrder()/item.getAmountLimit())*100;
            infoJson.setPartcipantPercent(percentage); // 참여율 계산하여 전달
            switch (item.getNowTab()) { // 현재 탭이 어디인지에 따라 전달내용 넣
                case 5:
                    infoJson.setFiveContents(item.getItemTab5().getContents());
                    infoJson.setFiveLocationDetail(item.getItemTab5().getLocationDetail());
                case 4:
                    infoJson.setFourContents(item.getItemTab4().getContents());
                    infoJson.setFourArrivedTime(item.getItemTab4().getArrivedTime().toString());
                case 3:
                    infoJson.setThreeContents(item.getItemTab3().getContents());
                case 2:
                    infoJson.setTwoContents(item.getItemTab2().getContents());
                    infoJson.setTwoEndDate(item.getItemTab2().getEndDate().toString());
                default:
                    infoJson.setOneContents(item.getItemTab1().getContents());
                    infoJson.setOneEndDate(item.getItemTab1().getEndDate().toString());
                    infoJson.setOneLocation(item.getItemTab1().getLocation());
            }

            infoJson.setImgList(item.getImgPaths());
        }
        else infoJson.setIsDeleted(item.getIsDeleted()); // item이 삭제된 경우 삭제되었다는 값만 전달

        return infoJson;
    }

    /*
    * 공구 item의 내용을 수정할때의 service
    * parameter
    *   itemId, info (item_id, 변동된 tab의 내용 필수)
    * return
    *   true : item update success
    *   false : item update fail
    * */
    public Boolean updateItem(String itemId, ItemAcceptJson info) {
        Boolean result = true;
        Item parentsItem = itemRepository.getOne(Long.parseLong(info.getA_itemId()));

        if(info.getItemTitle() != null) parentsItem.setTitle(info.getItemTitle());
        if(info.getItemAmountLimit() != null) parentsItem.setAmountLimit(Integer.valueOf(info.getItemAmountLimit()));
        if(Integer.valueOf(info.getA_TabNumber()) != 1) // 탭1일 아닐 경우 수정
            if(info.getItemNumOfOrder() != null) parentsItem.setNumOfOrder(Integer.valueOf(info.getItemNumOfOrder()));
        if(info.getItemCategory() != null) { // 카테고리 수정
            Category getCategory = categoryRepository.findByCategory(info.getItemCategory());
            parentsItem.setCategory(getCategory);
        }

        itemRepository.save(parentsItem);

        return result;
    }

    /*
     * 공구 tab의 내용을 수정할때의 service
     * acceptJson
     *   itemId, a_editTab, 변동된 tab의 내용
     * return
     *   true : item update success
     *   false : item update fail
     * */
    public Boolean patchItemTab(ItemAcceptJson acceptJson){
        Boolean result = true;
        Item parentsItem = itemRepository.getOne(Long.parseLong(acceptJson.getA_itemId()));
        this.changeTabImgs(Integer.valueOf(acceptJson.getA_TabNumber()) , acceptJson.getA_imgPathList() ,parentsItem);

        switch (acceptJson.getA_TabNumber()){
            case "1" :
                ItemTab1 tab1 = parentsItem.getItemTab1();
                if(acceptJson.getA_editTab()) {
                    if(!acceptJson.getOneContents().isEmpty()) tab1.setContents(acceptJson.getOneContents());
                    if(!acceptJson.getOneLocation().isEmpty()) tab1.setLocation(acceptJson.getOneLocation());
                    if(!acceptJson.getOneEndDate().isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // tab1에서 년월일만 입력받아서 이렇게 설정함
                        try {
                            Date newEndDate = sdf.parse(acceptJson.getOneEndDate());
                            tab1.setEndDate(newEndDate);
                        } catch (ParseException e) {
                            result = false;
                            e.printStackTrace();
                        }
                    }
                    parentsItem.setItemTab1(tab1);
                }
                break;
            case "2" :
                // optionString contents img_path
                ItemTab2 tab2 = parentsItem.getItemTab2();
                if(!acceptJson.getTwoContents().isEmpty()) tab2.setContents(acceptJson.getTwoContents());
                if(!acceptJson.getTwoOptionString().isEmpty()) tab2.setOptionString(acceptJson.getTwoOptionString());
                parentsItem.setItemTab2(tab2);
                break;
            case "3" :
                ItemTab3 tab3 = parentsItem.getItemTab3();
                if(!acceptJson.getThreeContents().isEmpty()) tab3.setContents(acceptJson.getThreeContents());
            case "4" :
                ItemTab4 tab4 = parentsItem.getItemTab4();
                if(!acceptJson.getFourArrivedTime().isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // tab1에서 년월일만 입력받아서 이렇게 설정함
                    try {
                        Date newArriveDate = sdf.parse(acceptJson.getFourArrivedTime());
                        tab4.setArrivedTime(newArriveDate);
                    } catch (ParseException e) {
                        result = false;
                        e.printStackTrace();
                    }
                }
                if(!acceptJson.getFourContents().isEmpty()) tab4.setContents(acceptJson.getFourContents());
                parentsItem.setItemTab4(tab4);
                break;
            case "5" :
                ItemTab5 tab5 = parentsItem.getItemTab5();
                if(!acceptJson.getFiveLocationDetail().isEmpty()) tab5.setLocationDetail(acceptJson.getFiveLocationDetail());
                if(!acceptJson.getFiveContents().isEmpty()) tab5.setContents(acceptJson.getFiveContents());
                parentsItem.setItemTab5(tab5);
                break;
        }

        if(!acceptJson.getA_editTab()) {
            parentsItem.setNowTab(Integer.valueOf(acceptJson.getA_TabNumber()));
        }
        itemRepository.save(parentsItem);
        return result;
    }

    /*
     * img list change
     * parameter
     *   tab , new_imgpaths , target Item
     * return
     *   void
     * */
    public void changeTabImgs(Integer tab,String[] imgpaths,Item target){

        List<ItemImgPath> list =  target.getImgPaths();
        list.forEach(img->{
            if(img.getTab().equals(tab)){
                list.remove(img);
            }
        });

        for (String newImgString : imgpaths) {
            ItemImgPath newImgPath = new ItemImgPath();
            newImgPath.setTab(tab);
            newImgPath.setImg_path(newImgString);
            list.add(newImgPath);
        }

        target.setImgPaths(list);
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
     * return
     *   boolean
     * */
    public boolean insertParticipantUser(JoinAcceptJson acceptJson){
        boolean result = true;

        Item parentItem = itemRepository.getOne(Long.parseLong(acceptJson.getItemId()));
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
        itemRepository.save(parentItem);

        return result;
    }
}
