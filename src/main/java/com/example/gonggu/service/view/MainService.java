package com.example.gonggu.service.view;

import com.example.gonggu.domain.category.Category;
import com.example.gonggu.domain.item.Item;
import com.example.gonggu.persistence.category.CategoryRepository;
import com.example.gonggu.persistence.item.ItemRepository;
import com.example.gonggu.service.item.PopularItemList;
import com.example.gonggu.service.item.RecentItemList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;

    // 인기글 보여지는 개수
    private final int NUMOFPOPULAR = 5;

    // 최신글에 보여지는 개수
//    private final int NUMOFRECENT = 5;

    // 인기글과 관련한 서비스

    /*
     * 메인화면에서 인기글 Service
     * return
     *      List<PopularItemList> 인기있는 아이템 정보 전달
     *  */
    public List<PopularItemList> getPopularBoard () {
        List<Item> items = itemRepository.findByOrderByNumOfLikeDesc();
        List<PopularItemList> results = new ArrayList<>();
        PopularItemList popularItemList;

        for (int i = 0; i < NUMOFPOPULAR; i++) {
            popularItemList = new PopularItemList();

            popularItemList.setItemId(items.get(i).getItemId());
            popularItemList.setTitle(items.get(i).getTitle());                              // 공구 title
            popularItemList.setParticipantNum(items.get(i).getNumOfOrder());                // 지금까지 구매된 수량
            popularItemList.setAmountLimit(items.get(i).getAmountLimit());                  // 공구주문까지 최소 수량

            Integer percentage = (items.get(i).getNumOfOrder()/items.get(i).getAmountLimit())*100;
            popularItemList.setParticipantPercent(percentage);                              // 진행률

            popularItemList.setImgPath(items.get(i).getThumnail());                         // item의 대표이미지

            results.add(popularItemList);
        }
        System.out.println(results);
        return results;
    }

    /*
     * 메인화면에서 최신글 + 최신글더보기 Service
     * return
     *      List<RecentItemList> 최신에 등록된 아이템 정보 5개 전달
     *  */
    public List<RecentItemList> getRecentBoard(int howManyItem) {
        List<Item> items = itemRepository.findByOrderByRegDateDesc();
        List<RecentItemList> result = new ArrayList<>();
        RecentItemList recentItemList;

        if(howManyItem == 0 ) howManyItem = items.size(); // 메인화면에서 최신글 더보기를 누른 경우

        for(int i = 0; i<howManyItem; i++) {
            recentItemList = new RecentItemList();
            recentItemList.setItemId(items.get(i).getItemId());
            recentItemList.setTitle(items.get(i).getTitle());
            recentItemList.setCurrentTap(items.get(i).getNowTab());

            switch (recentItemList.getCurrentTap()) {
                case 1:  // Tab1인 경우
                    recentItemList.setDueDate(items.get(i).getItemTab1().getEndDate());
                    recentItemList.setLikeNum(items.get(i).getNumOfLike());
                    break;
                case 2:  // Tab2인 경우
                    recentItemList.setDueDate(items.get(i).getItemTab2().getEndDate());
                    recentItemList.setAmountLimit(items.get(i).getAmountLimit());                 // 공구주문까지 최소수량
                    recentItemList.setParticipantNum(items.get(i).getNumOfOrder());               // 지금까지 구매된 수량
                    Integer percentage = (items.get(i).getNumOfOrder()/items.get(i).getAmountLimit())*100;
                    recentItemList.setParticipantPercent(percentage);                              // 진행률
                    break;
                default: // Tab5인 경우
                    break;
            }
            recentItemList.setImgPath(items.get(i).getThumnail()); // item의 대표이미지 설정
            result.add(recentItemList);
        }
        return result;
    }

    /*
     * 카테고리별 아이템 검색
     * parameter
     *   acceptJson
     * return
     *   List<Item>
     * */
    public List<Item> apiGetCategoryItem(Long categoryId){
        Category cate = categoryRepository.findOne(categoryId);
        List<Item> list = itemRepository.findByOrderByCategoryDesc(cate);

        return list;
    }

}
