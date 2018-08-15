package com.example.gonggu.service.item;

import com.example.gonggu.controller.item.ItemAcceptJson;
import com.example.gonggu.domain.item.Item;
import com.example.gonggu.domain.item.ItemTab2;
import com.example.gonggu.domain.item.ListOfLikeForItem;
import com.example.gonggu.persistence.item.ItemRepository;
import com.example.gonggu.persistence.item.ListOfLikeForItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.*;

@Configuration
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ListOfLikeForItemRepo likeRepo;

    // 인기글 보여지는 개수
    private final int NUMOFPOPULAR = 5;

    // 최신글에 보여지는 개수
    private final int NUMOFRECENT = 10;


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

    public boolean patchItemTab(ItemAcceptJson acceptJson){
        Map<String, Object> results = new HashMap<>();
        Item parentsItem = itemRepository.getOne(Long.parseLong(acceptJson.getA_itemId()));

        switch (acceptJson.getA_TabNumber()){
            case "2" :

                // optionString contents img_path
                ItemTab2 tab2 = parentsItem.getItemTab2();
                if(!acceptJson.getTwoImgPath().isEmpty()) tab2.setImgPath(acceptJson.getTwoImgPath());
                if(!acceptJson.getTwoContents().isEmpty()) tab2.setContents(acceptJson.getTwoContents());
                if(!acceptJson.getTwoOptionString().isEmpty()) tab2.setOptionString(acceptJson.getTwoOptionString());
                // 여기를 set 해줘야겠지?? 바로 save를 호출해도 되려나?? @Hyeontae
                parentsItem.setItemTab2(tab2);
                itemRepository.save(parentsItem);

                break;
            case "3" :
                System.out.println("3");
                break;
            case "4" :
                System.out.println("2");
                break;
            case "5" :
                System.out.println("2");
                break;

        }

        return true;
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
}
