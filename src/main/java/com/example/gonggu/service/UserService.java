package com.example.gonggu.service;

import com.example.gonggu.domain.User;
import com.example.gonggu.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void signUp(User user) {
        userRepository.save(user);
    }

    public void readAllUser(){
        this.userRepository.findAll().forEach(usr ->{
            System.out.println(usr);
        });
    }

    public void testInsert(){
        User user = new User();
        user.setUserId("a");
        user.setUserPW("1");
        user.setUserName("sy");
        user.setAccounBank("sss");
        user.setAccountHolder("s");
        user.setAccountNum("s");
        user.setRegDate("1111");
        user.setUpdateDate("2222");

        userRepository.save(user);
    }

    public void testUpdate(String userId){
        User user =  userRepository.findByUserId(userId);
        user.setUserName("수정");
        userRepository.save(user);

    }

//    public User find(String userId) {
//       return userRepository.findByUserId()
//    }
}
