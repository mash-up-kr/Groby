package com.example.gonggu.service.user;

import com.example.gonggu.dto.user.*;
import com.example.gonggu.domain.user.User;
import com.example.gonggu.persistence.user.NotificationRepository;
import com.example.gonggu.persistence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public UserInfo getUserBy(Map<String,Object> acceptMap){
        User user;
        if(acceptMap.get("getUserBy").toString().equals("Email")){
            user = userRepository.findByUserEmail(acceptMap.get("userEmail").toString());
        }else{
            user = userRepository.findOne(Long.parseLong(acceptMap.get("userId").toString()));
        }
        UserInfo result = new UserInfo();
        result.setUserId(user.getUserId());
        result.setAccountBank(user.getAccountBank());
        result.setAccountHolder(user.getAccountHolder());
        result.setAccountNum(user.getAccountNum());
        result.setIsDeleted(user.getIsDeleted());
        result.setUserEmail(user.getUserEmail());
        result.setUserName(user.getUserName());

        return result;
    }

    public void createUser(UserSignupJson acceptJson) {
        User user = new User();
        user.setUserEmail(acceptJson.getUserEmail());
//        user.setUserPw(bCryptPasswordEncoder.encode(acceptJson.getUserPw()));
        user.setUserPw(acceptJson.getUserPw());
        user.setUserName(acceptJson.getUserName());
        user.setUserToken(acceptJson.getUserToken());

        // signup 단계에서 계좌 정보를 받지 않음
//        if(info.get("userAccountBank") != null){
//            user.setAccountBank(info.get("userAccountBank").toString());
//            user.setAccountHolder(info.get("userAccountHolder").toString());
//            user.setAccountNum(info.get("userAccountNum").toString());
//        }

        userRepository.save(user);
    }

    public void userUpdate(UserPatchJson acceptJson) {
        User user = userRepository.findByUserEmail(acceptJson.getUserEmail());

        user.setUserName(acceptJson.getUserName());
        user.setAccountBank(acceptJson.getAccountBank());
        user.setAccountHolder(acceptJson.getAccountHolder());
        user.setAccountNum(acceptJson.getAccountNum());

        userRepository.save(user);
    }

    public void userSetPassword(UserPwJson acceptJson){
        User user = userRepository.findByUserEmail(acceptJson.getUserEmail());
//        user.setUserPw(bCryptPasswordEncoder.encode(acceptJson.getUserPw()));
        user.setUserPw(acceptJson.getUserPw());

        userRepository.save(user);
    }


//    public boolean loginUser(UserLoginJson acceptJson) {
//        User checkUser = userRepository.findByUserEmail(acceptJson.getUserEmail());
//
////        if (checkUser.getUserPw() == bCryptPasswordEncoder.encode(acceptJson.getUserPw()))
//        if (checkUser.getUserPw() == acceptJson.getUserPw())
//            return true;
//        else
//            return false;
//    }

    public UserInfo loginUser(UserLoginJson acceptJson) {
        User checkUser = userRepository.findByUserEmail(acceptJson.getUserEmail());

//        if (checkUser.getUserPw() == bCryptPasswordEncoder.encode(acceptJson.getUserPw()))
        if (checkUser.getUserPw() == acceptJson.getUserPw()){
            UserInfo result = new UserInfo();
            result.setUserId(checkUser.getUserId());
            result.setAccountBank(checkUser.getAccountBank());
            result.setAccountHolder(checkUser.getAccountHolder());
            result.setAccountNum(checkUser.getAccountNum());
            result.setIsDeleted(checkUser.getIsDeleted());
            result.setUserEmail(checkUser.getUserEmail());
            result.setUserName(checkUser.getUserName());
            return result;
        }
        else {
            return null;
        }
    }


    // for Developer not for Service
    public boolean deleteUser(String userId) {
        User checkUser = userRepository.getOne(Long.valueOf(userId));

        if(checkUser == null){
            return false;
        }

        userRepository.delete(checkUser);
        return true;
    }

    public Boolean checkEmail(String userEmail){
        if(userRepository.findByUserEmail(userEmail) == null){
            return true;
        }else
            return false;
    }


    public String sendMail(String userEmail) {
        String key = new TempKey().getKey(4, true);

        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom("mashupdutchmarket@gmail.com");
        msg.setTo(userEmail);
        msg.setSubject("Dutch Market 인증번호");
        msg.setText("Dutch Market 에서 인증 메일을 보냅니다. \n\""+key+ "\" 를 앱에서 입력해주세요 \n By Groby");

        this.sender.send(msg);

        return key;
    }

    //info Map<String,Object>
    //return 타입 map<String,String>
    //학교 이매일 인증
    // User <- 토큰을 저장 User.getEmailAuth
    // /checkEmail/{id}/{EmailAuth}
    // User.getEmailAuth == parameter EmailAuth 일치?

}