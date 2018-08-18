package com.example.gonggu.service.item;

import com.example.gonggu.controller.item.ItemAcceptJson;
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

    // 인기글 보여지는 개수
    private final int NUMOFPOPULAR = 5;

    // 최신글에 보여지는 개수
    private final int NUMOFRECENT = 5;

    // 인기글과 관련한 서비스
    public Map<String,Object> getPopularBoard () {
        List<Item> items = itemRepository.findByOrderByNumOfLikeDesc();
        Map<String, Object> results = new HashMap<>();
        PopularItemList popularItemList;

        for (int i = 0; i < NUMOFPOPULAR; i++) {
            popularItemList = new PopularItemList();

            popularItemList.setItemId(items.get(i).getItemId());
            popularItemList.setTitle(items.get(i).getTitle());                              // 공구 title
            popularItemList.setParticipantNum(items.get(i).getNumOfOrder());                  // 지금까지 구매된 수량
            popularItemList.setAmountLimit(items.get(i).getAmountLimit());    // 공구까지 최소 수량
            popularItemList.setImgPath(items.get(i).getItemTab1().getImgPath());            // 탭 1에서 총대가 설정한 이미지

            results.put(String.valueOf(i), popularItemList);
        }
        System.out.println(results);
        return results;
    }

    // 최신글과 관련한 서비스
    public List<RecentItemList> getRecentBoard() {
        List<Item> items = itemRepository.findByOrderByRegDateDesc();
        List<RecentItemList> result = new ArrayList<>();
        RecentItemList recentItemList;

        for(int i = 0; i<NUMOFRECENT; i++) {
            recentItemList = new RecentItemList();
            recentItemList.setItemId(items.get(i).getItemId());
            recentItemList.setTitle(items.get(i).getTitle());
            recentItemList.setCurrentTap(items.get(i).getNowTab());

            switch (recentItemList.getCurrentTap()) {
                case 1:  // Tab1인 경우
                    recentItemList.setDueDate(items.get(i).getItemTab1().getEndDate());
                    recentItemList.setLikeNum(items.get(i).getNumOfLike());
                    recentItemList.setImgPath(items.get(i).getItemTab1().getImgPath());
                    break;
                case 2:  // Tab2인 경우
                    recentItemList.setDueDate(items.get(i).getItemTab2().getEndDate());
                    recentItemList.setAmountLimit(items.get(i).getAmountLimit()); // 공구까지 최소수량
                    recentItemList.setParticipantNum(items.get(i).getNumOfOrder());               // 지금까지 구매된 수량
                    recentItemList.setImgPath(items.get(i).getItemTab1().getImgPath());         // 메인화면에 보여지는 이미지는 항상 첫번째 공구때 올린 사진
                    break;
                case 4:  // Tab4인 경우
                    recentItemList.setImgPath(items.get(i).getItemTab4().getReceiptImgPath());
                    break;
                default: // Tab5인 경우
                    break;
            }

            result.add(recentItemList);
        }
        return result;
    }

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
        for(ListOfLikeForItem temp :item.getLikeForItemList())
            if(temp.getUserEmail().equals(acceptJson.getUserLikeEmail()) ){
                item.getLikeForItemList().remove(temp);
                likeRepo.delete(temp.getLikeId());
                result = false; break;
            }

        if(result) {
            ListOfLikeForItem newlike = new ListOfLikeForItem();
            newlike.setUserEmail(acceptJson.getUserLikeEmail().toString());
            item.getLikeForItemList().add(newlike);
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
        ItemTab4 itemTab4 = new ItemTab4();
        ItemTab5 itemTab5 = new ItemTab5();

        // 공구 item에 대한 기본 설정
        item.setNowTab(Integer.parseInt(acceptJson.getA_TabNumber()));
        Category getCategory = categoryRepository.findByCategory(acceptJson.getItemCategory());
        item.setCategory(getCategory);
        item.setTitle(acceptJson.getItemTitle());
        User getUser = userRepository.findByUserEmail(acceptJson.getItemUserEmail());
        item.setUser(getUser);
        item.setAmountLimit(Integer.parseInt(acceptJson.getItemAmountLimit())); // item의 최소공구수량 설정

        // 공구 item tab1 설정
        itemTab1.setContents(acceptJson.getOneContents());
        itemTab1.setLocation(acceptJson.getOneLocation());
        itemTab1.setImgPath(acceptJson.getOneImgPath());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // tab1에서 년월일만 입력받아서 이렇게 설정함
        try {
            Date insertDate = sdf.parse(acceptJson.getOneEndDate());
            itemTab1.setEndDate(insertDate);
        } catch (ParseException e) {
            result = false;
            e.printStackTrace();
        }
        item.setItemTab1(itemTab1);

        // 공구 item tab2,4,5 null 값으로 생성
        item.setItemTab2(itemTab2);
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
                    infoJson.setFourImgPath(item.getItemTab4().getReceiptImgPath());
                case 3:
                case 2:
                    infoJson.setTwoContents(item.getItemTab2().getContents());
                    infoJson.setTwoEndDate(item.getItemTab2().getEndDate().toString());
                    infoJson.setTwoImgPath(item.getItemTab2().getImgPath());
                default:
                    infoJson.setOneContents(item.getItemTab1().getContents());
                    infoJson.setOneEndDate(item.getItemTab1().getEndDate().toString());
                    infoJson.setOneImgPath(item.getItemTab1().getImgPath());
                    infoJson.setOneLocation(item.getItemTab1().getLocation());
            }
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
    public Boolean updateItem(String itemId, Map<String,Object> info) {
        Boolean result = true;
        Item parentsItem = itemRepository.getOne(Long.parseLong(info.get("itemId").toString()));


        itemRepository.save(parentsItem);
        return result;
    }


    public Boolean patchItemTab(ItemAcceptJson acceptJson){
        Boolean result = true;
        Item parentsItem = itemRepository.getOne(Long.parseLong(acceptJson.getA_itemId()));

        switch (acceptJson.getA_TabNumber()){
            case "1" :
                ItemTab1 tab1 = parentsItem.getItemTab1();
                if(acceptJson.getEditTab()) {
                    if(!acceptJson.getOneContents().isEmpty()) tab1.setContents(acceptJson.getOneContents());
                    if(!acceptJson.getOneLocation().isEmpty()) tab1.setLocation(acceptJson.getOneLocation());
                    if(!acceptJson.getOneImgPath().isEmpty()) tab1.setImgPath(acceptJson.getOneImgPath());
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
                if(!acceptJson.getTwoImgPath().isEmpty()) tab2.setImgPath(acceptJson.getTwoImgPath());
                if(!acceptJson.getTwoContents().isEmpty()) tab2.setContents(acceptJson.getTwoContents());
                if(!acceptJson.getTwoOptionString().isEmpty()) tab2.setOptionString(acceptJson.getTwoOptionString());
                parentsItem.setItemTab2(tab2);
                break;
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
                if(!acceptJson.getFourReceiptImgPath().isEmpty()) tab4.setReceiptImgPath(acceptJson.getFourReceiptImgPath());
                parentsItem.setItemTab4(tab4);
                break;
            case "5" :
                ItemTab5 tab5 = parentsItem.getItemTab5();
                if(!acceptJson.getFiveLocationDetail().isEmpty()) tab5.setLocationDetail(acceptJson.getFiveLocationDetail());
                if(!acceptJson.getFiveContents().isEmpty()) tab5.setContents(acceptJson.getFiveContents());
                parentsItem.setItemTab5(tab5);
                break;
        }
        if(!acceptJson.getEditTab()) {
            parentsItem.setNowTab(Integer.parseInt(acceptJson.getA_TabNumber()));
        }
        itemRepository.save(parentsItem);
        return result;
    }
}
