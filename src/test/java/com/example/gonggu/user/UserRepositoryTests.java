package com.example.gonggu.user;

import com.example.gonggu.domain.user.User;
import com.example.gonggu.domain.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testInsertUsers() {
        IntStream.range(1,10).forEach(i->{
            User user = new User();
            user.setUserId("user" + i);
            user.setUserPw("pw"+i);
            user.setUserName("name" + i);
            user.setUserEmail("email"+i);
            user.setAccountNum("account_num"+i);
            user.setAccountBank("account_bank"+i);
            user.setAccountHolder("account_holder"+i);

            userRepository.save(user);
        });
    }

}