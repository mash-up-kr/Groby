package com.example.gonggu.service.view;

import com.example.gonggu.dto.view.ItemCard;
import com.example.gonggu.domain.category.Category;
import com.example.gonggu.domain.item.Item;
import com.example.gonggu.persistence.category.CategoryRepository;
import com.example.gonggu.persistence.item.ItemRepository;
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
    public List<ItemCard> getPopularBoard () {
        List<Item> items = itemRepository.findByOrderByNumOfLikeDesc();
        List<ItemCard> results = new ArrayList<>();

        for (int i = 0; i < NUMOFPOPULAR; i++) {
            ItemCard itemCard = new ItemCard();

            itemCard.setItemId(items.get(i).getItemId().toString());
            itemCard.setTitle(items.get(i).getTitle());                              // 공구 title
            itemCard.setParticipantNum(items.get(i).getNumOfOrder());                // 지금까지 구매된 수량
            itemCard.setAmountLimit(items.get(i).getAmountLimit());                  // 공구주문까지 최소 수량

            Integer percentage = (items.get(i).getNumOfOrder()/items.get(i).getAmountLimit())*100;
            itemCard.setParticipantPercent(percentage);                              // 진행률

            itemCard.setThumnailURL(items.get(i).getThumnail());                         // item의 대표이미지

            results.add(itemCard);
        }

        return results;
    }

    /*
     * 메인화면에서 최신글 + 최신글더보기 Service
     * return
     *      List<RecentItemList> 최신에 등록된 아이템 정보 5개 전달
     *  */
    public List<ItemCard> getRecentBoard(int howManyItem) {
        List<Item> items = itemRepository.findByOrderByRegDateDesc();
        List<ItemCard> result = new ArrayList<>();

        if(howManyItem == 0 ) howManyItem = items.size(); // 메인화면에서 최신글 더보기를 누른 경우

        for(int i = 0; i<howManyItem; i++) {
            ItemCard itemCard = new ItemCard();
            itemCard.setItemId(items.get(i).getItemId().toString());
            itemCard.setTitle(items.get(i).getTitle());
            itemCard.setNowTab(items.get(i).getNowTab());

            switch (itemCard.getNowTab()) {
                case 1:  // Tab1인 경우
                    itemCard.setDueDate(items.get(i).getItemTab1().getEndDate());
                    itemCard.setLikeNum(items.get(i).getNumOfLike().toString());
                    break;
                case 2:  // Tab2인 경우
                    itemCard.setDueDate(items.get(i).getItemTab2().getEndDate());
                    itemCard.setAmountLimit(items.get(i).getAmountLimit());                 // 공구주문까지 최소수량
                    itemCard.setParticipantNum(items.get(i).getNumOfOrder());               // 지금까지 구매된 수량
                    Integer percentage = (items.get(i).getNumOfOrder()/items.get(i).getAmountLimit())*100;
                    itemCard.setParticipantPercent(percentage);                              // 진행률
                    break;
                default: // Tab5인 경우
                    break;
            }
            itemCard.setThumnailURL(items.get(i).getThumnail()); // item의 대표이미지 설정
            result.add(itemCard);
        }
        return result;
    }

    /*
     * 카테고리별 아이템 검색
     * parameter
     *   acceptJson
     * return
     *   List<Item> -> List<ItemCard>
     *   Item 으로 하면 불필요한 정보들까지 가져와서 SQL 이 늘어남 (FetchType LAZY가 의미가 없다.)
     *   이를 해결하기 위해서 ItemCard 를 만들어 필요한 정보들만 조회할수 있도록 함
     * */
    public List<ItemCard> apiGetCategoryItem(Long categoryId){
        Category cate = categoryRepository.findOne(categoryId);
        List<ItemCard> list = new ArrayList<>();
        itemRepository.findByCategoryOrderByRegDateDesc(cate).forEach(it->{
            ItemCard card = new ItemCard();
            card.setTitle(it.getTitle());
            card.setNowTab(it.getNowTab());
            card.setEndDate(it.getItemTab1().getEndDate());
            card.setItemId(it.getItemId().toString());
            card.setThumnailURL(it.getThumnail());
            if(it.getNowTab() == 1)
                card.setLikeNum(it.getNumOfLike().toString());
            else
                card.setProgress((it.getNumOfOrder()/it.getAmountLimit())*100);

            list.add(card);
        });


        return list;
    }

}
