package com.example.gonggu.service;

import com.example.gonggu.domain.User;
import com.example.gonggu.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(Map<String,String> info){
        User user = new User();
        user.setUserId(info.get("userId"));
        user.setUserPW(info.get("userPw"));
//        user.setUserPW(passwordEncoder.encode(info.get("userPW")));
        user.setUserName(info.get("userName"));
        user.setAccounBank(info.get("accountBank"));
        user.setAccountHolder(info.get("accoutnHolder"));
        user.setAccountNum(info.get("accountNum"));

        userRepository.save(user);
    }

    public void updateUser(Map<String,String> info){
        User user = new User();

    }

    public void testUpdate(String userId){
        User user =  userRepository.findByUserId(userId);
        user.setUserName("수정");
//        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);

    }

    public boolean loginUser(Map<String,String> info){
        User checkUser = userRepository.findByUserId(info.get("userId"));
        // checkUser.getUserPW()
        // info.get("userPw"); -> encrypt
        //
        return true;
    }



}
