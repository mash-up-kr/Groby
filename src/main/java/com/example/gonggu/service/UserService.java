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
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createUser(Map<String, String> info) {
        User user = new User();
        user.setUserId(info.get("userId"));
        user.setUserPW(info.get("userPw"));
        user.setUserPW(bCryptPasswordEncoder.encode(info.get("userPW")));
        user.setUserName(info.get("userName"));
        user.setAccounBank(info.get("accountBank"));
        user.setAccountHolder(info.get("accoutnHolder"));
        user.setAccountNum(info.get("accountNum"));

        userRepository.save(user);
    }

    public void userUpdate(Map<String, String> info) {
//        try {
//
//        }catch (Exception e ){
//
//        }
        User user = userRepository.findByUserId(info.get("userId"));
        user.setUserName(info.get("userName"));
        user.setAccounBank(info.get("userAccountBank"));
        user.setAccountHolder(info.get("userAccountHolder"));
        user.setAccountNum(info.get("userAccountNum"));
        userRepository.save(user);
    }

//    public void userSetPassword(){
//        User user = userRepository.findByUserId(info.get("userId"));
//        user.setUserPW(bCryptPasswordEncoder.encode(info.get("userPW")));
//    }


    public boolean loginUser(Map<String, String> info) {
        User checkUser = userRepository.findByUserId(info.get("userId"));

        if (checkUser.getUserPW() == bCryptPasswordEncoder.encode(info.get("userPW")))
            return true;

        else
            return false;
    }


    //info Map<String,Object>
    //return 타입 map<String,String>
    //학교 이매일 인증
    // User <- 토큰을 저장 User.getEmailAuth
    // /checkEmail/{id}/{EmailAuth}
    // User.getEmailAuth == parameter EmailAuth 일치?

}