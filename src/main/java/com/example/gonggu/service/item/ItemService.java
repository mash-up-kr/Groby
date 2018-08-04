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
    public List<Map<String,Object>> getPopularBoard () {
        List<Item> items = itemRepository.findByOrderByLikeNumDesc();

        List<Map<String, Object>> results = new ArrayList<>();
        Map<String, Object> itemDetail = new HashMap<>();

        for (int i = 0; i < NUMOFPOPULAR; i++) {
            itemDetail.put("itemId", items.get(i).getItemId());
            itemDetail.put("title", items.get(i).getTitle());
            itemDetail.put("img", "이미지 주소");
            results.add(itemDetail);
        }
//        return results;
        System.out.println(results);
        return results;
    }

    // 최신글과 관련한 서비스
    public Map<String, Object> getRecentBoard() {
        List<Item> items = itemRepository.findByOrderByRegDateDesc();
        Map<String, Object> result = new HashMap<>();
        RecentItemList recentItemList;

        for(int i = 0; i<NUMOFRECENT; i++) {
            recentItemList = new RecentItemList();
            recentItemList.setItemId(items.get(i).getItemId());
            recentItemList.setTitle(items.get(i).getTitle());
            recentItemList.setCurrentTap(items.get(i).getNowTab());

            switch (recentItemList.getCurrentTap()) {
                case 1:  // Tab1인 경우
                    recentItemList.setDueDate(items.get(i).getItemTab1().getEndDate1());
                    recentItemList.setLikeNum(items.get(i).getLikeNum());
                    recentItemList.setImgPath(items.get(i).getItemTab1().getImgPath());
                    break;
                case 2:  // Tab2인 경우
                    recentItemList.setDueDate(items.get(i).getItemTab2().getEndDate2());
                    recentItemList.setParticipantNum(items.get(i).getItemTab2().getAmountLimit()); // Tab2에서 보여지는 amount limit이 현재까지 참여한 수인가?
                    recentItemList.setAmountLimit(items.get(i).getTotalNum()); // 이게 참여한 사람의 수가 아닌가?
                    recentItemList.setImgPath(items.get(i).getItemTab2().getImgPath()); // 메인화면에 보여지는 이미지는 항상 첫번째 공구때 올린 사진...? 아님 총대가 바꿀때 마다 체인지?
                    break;
                case 4:  // Tab4인 경우
                    recentItemList.setImgPath(items.get(i).getItemTab4().getReceiptImgPath());
                    break;
                default: // Tab5인 경우
                    break;
            }

            result.put(String.valueOf(i), recentItemList);
        }
        return result;
    }
}
