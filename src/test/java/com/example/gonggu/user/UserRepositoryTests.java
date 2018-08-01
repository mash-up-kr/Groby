package com.example.gonggu.user;

import com.example.gonggu.domain.User;
import com.example.gonggu.domain.UserRepository;
import com.example.gonggu.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    UserRepository userRepository;

    @Test
    public void testInsert(){
        UserService user = new UserService(userRepository);
//        user.testInsert();
    }

    @Test
    public void testUpdate(){
        UserService user = new UserService(userRepository);
        user.testUpdate("a");
    }
}

