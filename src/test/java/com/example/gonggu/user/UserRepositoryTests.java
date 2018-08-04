package com.example.gonggu.user;

import com.example.gonggu.domain.user.User;
import com.example.gonggu.persistence.user.UserRepository;
import com.example.gonggu.service.user.UserService;
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
    UserService usrService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void testInsert(){
//        UserService user = new UserService(userRepository, bCryptPasswordEncoder);
        User user = new User();
        user.setUserId("1");
        user.setUserPW(bCryptPasswordEncoder.encode("123"));
        user.setUserName("4");
        user.setAccounBank("5");
        user.setAccountHolder("5");
        user.setAccountNum("6");

        userRepository.save(user);
    }

    @Test
    public void testCheck(){
        User usr = userRepository.findOne(5l);
        System.out.println(bCryptPasswordEncoder.matches("222",usr.getUserPW()));
    }

}

