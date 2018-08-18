package com.example.gonggu.item;

import com.example.gonggu.controller.item.ItemAcceptJson;
import com.example.gonggu.domain.category.Category;
import com.example.gonggu.domain.item.Item;
import com.example.gonggu.domain.item.ItemTab1;
import com.example.gonggu.domain.item.ListOfLikeForItem;
import com.example.gonggu.domain.user.User;
import com.example.gonggu.persistence.category.CategoryRepository;
import com.example.gonggu.persistence.item.ItemRepository;
import com.example.gonggu.persistence.item.ListOfLikeForItemRepo;
import com.example.gonggu.persistence.user.UserRepository;
import com.example.gonggu.service.item.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Commit
public class ItemRepositoryTests {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ListOfLikeForItemRepo listLikeRepo;

    @Autowired
    private ItemService itemService;


   //  item테이블에 아이템 값을 입력하는 테스트
//   @Test
//    public void testInsertItem() {
//       User owner = userRepository.findByUserName("onemoon");
//       Item newItem = new Item();
//       newItem.setUser(owner);
//       newItem.setTitle("제목+잡화");
//       newItem.setCategory(categoryRepo.findOne(1L));
//
//       ItemTab1 newItemTab1 = new ItemTab1();
//       newItemTab1.setContents("제목입니다");
//       newItemTab1.setImgPath("s3URL");
//
//       newItem.setItemTab1(newItemTab1);
//
//       itemRepository.save(newItem);
//    }


//    @Test
//    public void testInsertTab1() {
//
//        User user = new User();
//
//        user.setUserEmail("dddd@naver.com");
//        user.setUserPw("ddddd");
//
//        Item testItem = new Item();
//
//        testItem.setUser(user);
//
//        testItem.setTitle("TestTitle3333");
//        testItem.setNumOfLike(0); // default 0 로 해야한다.
//        testItem.setItemNumOfOrder(0); // default 0 로 해야한다.
//        testItem.setNowTab(1); // default 1
//
//        ItemTab1 tab1 = new ItemTab1();
//        tab1.setContents("안녕하세요 총대입니다. 하하하22");
//
//        testItem.setItemTab1(tab1);
//
////        tab1.setItem(testItem);
//
//        itemRepository.save(testItem);
//
//    }
//
//    // item의 특정칼럼 전체를 업데이트테스트
//    @Test
//    public void testUpdateItem() {
//        List<Item> items = itemRepository.findByOrderByRegDateDesc();
//
//        items.forEach(item -> {
////            System.out.println(item);
////            item.setCategory("옷");
//            itemRepository.save(item);
//        });
//    }
//
//
//    // item의 특정칼럼 삭제
//    @Test
//    public void testDeleteItem() {
////       Item item = itemRepository.findOne(8L);
//
//       itemRepository.delete(3L);
//    }
//
//    // item의 날짜순으로 읽기
//    @Test
//    public void testReadItems() {
//       List<Item> items = itemRepository.findByOrderByRegDateDesc();
//
//       items.forEach(item -> {
//           System.out.println(item);
//       });
//    }

//    @Transactional
//    @Test
//    public void testLike(){
//        Item item = itemRepository.getOne(1L);
//        ListOfLikeForItem like = new ListOfLikeForItem();
//        like.setUserEmail("onemoon3@gmail.com");
//
//        boolean check = true;
//
//        for(ListOfLikeForItem temp :item.getLikeForItemList())
//            if(temp.getUserEmail().equals(like.getUserEmail()) ){
//                item.getLikeForItemList().remove(temp);
//                listLikeRepo.delete(temp.getLikeId());
//                check = false; break;
//            }
//
//
//        //false 면 삭제 되었음 true 면 추가해야함
//        if(check) item.getLikeForItemList().add(like);
//
//
//        item.setNumOfLike(item.getLikeForItemList().size());
//        itemRepository.save(item);
//    }

/*    @Test
    public void createItem() {
        ItemAcceptJson itemAcceptJson = new ItemAcceptJson();

        itemAcceptJson.setA_TabNumber("1");
        itemAcceptJson.setItemTitle("들어가라아아ㅏ앗");
        itemAcceptJson.setItemCategory("화장품");
        itemAcceptJson.setOneContents("블라블라 아이템 집어넣귀이ㅣㅇ");
        itemAcceptJson.setOneEndDate("2018.04.30 11:11:11");
        itemAcceptJson.setOneImgPath("http://sddfdsd");
        itemAcceptJson.setOneLocation("강남역이지러어엉");
        itemAcceptJson.setItemUserEmail("1");
        itemService.createItem(itemAcceptJson);
    }*/
}
