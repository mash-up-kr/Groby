package com.example.gonggu.service.item;

import com.example.gonggu.domain.item.Item;
import com.example.gonggu.persistence.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

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
}
