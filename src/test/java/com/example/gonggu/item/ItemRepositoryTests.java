package com.example.gonggu.item;

import com.example.gonggu.domain.item.Item;
import com.example.gonggu.domain.item.ItemTab1;
import com.example.gonggu.domain.user.User;
import com.example.gonggu.persistence.item.ItemRepository;
import com.example.gonggu.persistence.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepositoryTests {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;


    // item테이블에 아이템 값을 입력하는 테스트
//   @Test
//    public void testInsertItem() {
//        User user = userRepository.findByUserId("user2").get(0);
////        User user = userRepository.findOne(1L);
//
//        for (int i = 1; i < 5; i++) {
//            Item item = new Item();
//
//            item.setCategory("음식");
//            item.setNumOfLike(0);
//            item.setLocation("location"+i);
//            item.setTitle("title"+i);
//            item.setUser(user);
//            item.setNowTab(1);
//            item.setNumOfOrder(100);
//
//            itemRepository.save(item);
//        }
//    }


    @Test
    public void testInsertTab1() {

        User user = new User();

        user.setUserEmail("dddd@naver.com");
        user.setUserPw("ddddd");

        Item testItem = new Item();

        testItem.setUser(user);

        testItem.setTitle("TestTitle3333");
        testItem.setNumOfLike(0); // default 0 로 해야한다.
        testItem.setNumOfOrder(0); // default 0 로 해야한다.
        testItem.setNowTab(1); // default 1

        ItemTab1 tab1 = new ItemTab1();
        tab1.setContents("안녕하세요 총대입니다. 하하하22");

        testItem.setItemTab1(tab1);

//        tab1.setItem(testItem);

        itemRepository.save(testItem);

    }

    // item의 특정칼럼 전체를 업데이트테스트
    @Test
    public void testUpdateItem() {
        List<Item> items = itemRepository.findByOrderByRegDateDesc();

        items.forEach(item -> {
//            System.out.println(item);
            item.setCategory("옷");
            itemRepository.save(item);
        });
    }


    // item의 특정칼럼 삭제
    @Test
    public void testDeleteItem() {
//       Item item = itemRepository.findOne(8L);

       itemRepository.delete(3L);
    }

    // item의 날짜순으로 읽기
    @Test
    public void testReadItems() {
       List<Item> items = itemRepository.findByOrderByRegDateDesc();

       items.forEach(item -> {
           System.out.println(item);
       });
    }
}
