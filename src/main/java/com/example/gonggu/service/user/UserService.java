package com.example.gonggu.service.user;

import com.example.gonggu.domain.user.User;
import com.example.gonggu.persistence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MailSender sender;

    // 해당 유저를 찾아서 리턴해준다.
    // info 의 getUserBy Key를 통해서 메서드를 변경한다.
    public User getUserBy(Map<String,Object> info){
        User user;
        if(info.get("getUserBy").toString() == "Email"){
            user = userRepository.findByUserId(info.get("userEmail").toString());
        }else{
            // info.get("getUserBy").toString() == "userId"
            user = userRepository.findOne((long)info.get("userId"));
        }
        user.setUserPw(null);
        return user;
    }

    public void createUser(Map<String, Object> info) {
        User user = new User();
        user.setUserEmail(info.get("userEmail").toString());
        user.setUserPw(bCryptPasswordEncoder.encode(info.get("userPw").toString()));
        user.setUserName(info.get("userName").toString());

        // signup 단계에서 계좌 정보를 받지 않음
//        if(info.get("userAccountBank") != null){
//            user.setAccountBank(info.get("userAccountBank").toString());
//            user.setAccountHolder(info.get("userAccountHolder").toString());
//            user.setAccountNum(info.get("userAccountNum").toString());
//        }

        userRepository.save(user);
    }

    public void userUpdate(Map<String, Object> info) {
        User user = userRepository.findByUserId(info.get("userEmail").toString());

        user.setUserName(info.get("userName").toString());
        user.setAccountBank(info.get("userAccountBank").toString());
        user.setAccountHolder(info.get("userAccountHolder").toString());
        user.setAccountNum(info.get("userAccountNum").toString());

        userRepository.save(user);
    }

    public void userSetPassword(Map<String,Object> info){
        User user = userRepository.findByUserId(info.get("userEmail").toString());
        user.setUserPw(bCryptPasswordEncoder.encode(info.get("userPw").toString()));

        userRepository.save(user);
    }


    public boolean loginUser(Map<String, Object> info) {
        User checkUser = userRepository.findByUserId(info.get("userEmail").toString());

        if (checkUser.getUserPw() == bCryptPasswordEncoder.encode(info.get("userPW").toString()))
            return true;
        else
            return false;
    }


    // for Developer not for Service
    public boolean deleteUser(String userId) {
        User checkUser = userRepository.findByUserId(userId);

        if(checkUser == null){
            return false;
        }

        userRepository.delete(checkUser);
        return true;
    }


    public void sendMail(String userEmail) {
        String key = new TempKey().getKey(4, true);

        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom("groby@gmail.com");
        msg.setTo(userEmail);
        msg.setSubject("Groby 인증번호");
        msg.setText("Groby 에서 인증 메일을 보냅니다. \n \""+key+ "\" 를 앱에서 입력해주세요 \n By Groby");

        this.sender.send(msg);
    }

    //info Map<String,Object>
    //return 타입 map<String,String>
    //학교 이매일 인증
    // User <- 토큰을 저장 User.getEmailAuth
    // /checkEmail/{id}/{EmailAuth}
    // User.getEmailAuth == parameter EmailAuth 일치?

}