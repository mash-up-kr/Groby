package com.example.gonggu.item;

import com.example.gonggu.domain.item.Item;
import com.example.gonggu.persistence.item.ItemRepository;
import com.example.gonggu.domain.user.User;
import com.example.gonggu.persistence.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepositoryTests {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;


    // item테이블에 아이템 값을 입력하는 테스트
   @Test
    public void testInsertItem() {
        User user = userRepository.findByUserId("user2").get(0);
//        User user = userRepository.findOne(1L);

        for (int i = 1; i < 5; i++) {
            Item item = new Item();

            item.setCategory("음식");
            item.setLikeNum(0);
            item.setLocation("location"+i);
            item.setTitle("title"+i);
            item.setUser(user);
            item.setNowTab(1);
            item.setTotalNum(100);

            itemRepository.save(item);
        }
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
