package com.example.gonggu.service.item;

import com.example.gonggu.domain.item.Item;
import com.example.gonggu.persistence.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Map<String,Object>> getRecentBoard () {
        List<Item> items = itemRepository.findAll();

        List<Map<String, Object>> results = new ArrayList<>();
        Map<String, Object> itemDetail = new HashMap<>();


        // 클래스 만들기 - service에 클래스 생성

        for (int i = 0; i < NUMOFRECENT; i++) {
            itemDetail.put("itemId", items.get(i).getItemId());
            itemDetail.put("title", items.get(i).getTitle());

            if (items.get(i).getTitle() == "[수량조사]") {
                itemDetail.put("likeNum", items.get(i).getLikeNum());
                itemDetail.put("img", items.get(i).getItemTab1().getImgPath());
                itemDetail.put("dueDate", items.get(i).getItemTab1().getEndDate1());
            }
            else {
                itemDetail.put("img", items.get(i).getItemTab1().getImgPath());
                itemDetail.put("dueDate", items.get(i).getItemTab2().getEndDate2());
                itemDetail.put("particiNum", items.get(i).getItemTab2().getAmountLimit());
                itemDetail.put("amountLimit", items.get(i).getTotalNum());
            }

            itemDetail.put("dueDate", "마감날짜");
            results.add(itemDetail);
        }
//        return results;
        System.out.println(results);
        return results;
    }
}
