package com.example.gonggu.item;

import com.example.gonggu.domain.item.Item;
import com.example.gonggu.domain.item.ItemRepository;
import com.example.gonggu.domain.user.User;
import com.example.gonggu.domain.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepositoryTests {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testInsertItem() {
        User user = userRepository.findByUserId("user1").get(0);
//        User user = userRepository.findOne(1L);

        for (int i = 1; i < 5; i++) {
            Item item = new Item();

            item.setCategory("음식");
            item.setLikeNum(0);
            item.setLocation("location"+i);
            Date date = new Date();
            item.setRegDate(date);
            item.setTitle("title"+i);
            item.setUser(user);


            itemRepository.save(item);
        }
    }

}
