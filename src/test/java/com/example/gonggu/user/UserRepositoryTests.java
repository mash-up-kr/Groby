package com.example.gonggu.user;

import com.example.gonggu.domain.User;
import com.example.gonggu.domain.UserRepository;
import com.example.gonggu.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService usrserv;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void testInsert(){
//        UserService user = new UserService(userRepository, bCryptPasswordEncoder);
//        usrserv.testInsert();
    }

    @Test
    public void testCheck(){
        User usr = userRepository.findOne(5l);
        System.out.println(bCryptPasswordEncoder.matches("222",usr.getUserPW()));
    }

    @Test
    public void testUpdate(){
//        UserService user = new UserService(userRepository, bCryptPasswordEncoder);
//        user.testUpdate("a");
    }
}

