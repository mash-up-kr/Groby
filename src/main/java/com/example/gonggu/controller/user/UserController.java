package com.example.gonggu.controller.user;

import com.example.gonggu.domain.user.Notification;
import com.example.gonggu.dto.APIResponse;
import com.example.gonggu.domain.user.User;
import com.example.gonggu.dto.user.*;
import com.example.gonggu.service.user.NotiService;
import com.example.gonggu.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user/*")
public class UserController {
    /*
     user 관련 API controller
     @PathVariable(GET) 과 acceptJson(!GET) 을 통해서 정보를 받아온다.
     받아오는 json 이 정확해야 한다.
     return : HttpStatus && APIResponse Class 를 Entity에 포함시켜 보낸다.
    */


    @Autowired
    UserService userService;
    @Autowired
    NotiService notiService;

    // 유저 아이디를 통해서 유저 정보 리턴
    @GetMapping(value = "/email/{userEmail}")
    public ResponseEntity<APIResponse<UserInfo>> apiGetUserByEmail(
            @PathVariable String userEmail
    ){
        HttpStatus status = HttpStatus.OK;
//        APIResponse<User> response = APIResponse.<User>builder()
        APIResponse<UserInfo> response = new APIResponse<>();
        response.setStatus(status);
        response.setMessage("get user is done");
        response.setReturnJson(
                userService.getUserBy(
                        new HashMap<String,Object>(){{
                            put("getUserBy" , "Email");
                            put("userEmail" , userEmail);
                        }})
        );

        return new ResponseEntity<>(response, status);
    }

    // 유저 넘버 (PK) 를 통해서 유저 정보 리턴
    @GetMapping(value = "/usernum/{userNum}")
    public ResponseEntity<APIResponse<UserInfo>> apiGetUserByUserNum(
            @PathVariable String userNum
    ){

        HttpStatus status = HttpStatus.OK;
//        User user = userService.getUserBy(new HashMap<String,Object>(){{
//            put("getUserBy" , "userId");
//            put("userId",Long.valueOf(userNum));
//        }});

        APIResponse<UserInfo> response = new APIResponse<>();
        response.setStatus(status);
        response.setMessage("Create user is done");
        response.setReturnJson(
                userService.getUserBy(new HashMap<String,Object>(){{
                    put("getUserBy" , "userId");
                    put("userId",Long.valueOf(userNum));
                }})
        );

        return new ResponseEntity<>( response , status);
    }

    //    /user/checkemail?userEail=abc@naver.com
    // return 값 명시해줘야 한다.
    @GetMapping("/checkemail")
    public ResponseEntity<APIResponse> apiCheckEmail(
            @RequestParam String userEmail
    ){
        HttpStatus status = HttpStatus.OK;
        String message;
        // 이메일 체크
        // 인증이 되는 경우 이메일을 보내준다.
        // 인증번호를 보내준다.
        APIResponse response = new APIResponse<>();
        response.setStatus(status);

        if(userService.checkEmail(userEmail)){
            status = HttpStatus.OK;
            message = userService.sendMail(userEmail);
        }else{
            status = HttpStatus.NOT_ACCEPTABLE;
            message = "이메일이 중복 되었습니다.";
        }

        return new ResponseEntity<>( response , status);
    }

    // Signup 관련 , 유저를 생성
    @PostMapping("/")
    public ResponseEntity<APIResponse> apiCreateUser(
        @RequestBody UserSignupJson acceptJson
    ){
        HttpStatus status = HttpStatus.CREATED;
        userService.createUser(acceptJson);

        APIResponse response = new APIResponse<>();
        response.setStatus(status);
        response.setMessage("create user is done");
        response.setAcceptJson(acceptJson);

        return new ResponseEntity<>( response , status);
    }

    // 유저 로그인
    @PostMapping("/login")
    public ResponseEntity<APIResponse> apiLoginUser(
            @RequestBody UserLoginJson acceptJson
    ){
        HttpStatus status = HttpStatus.OK;



        String message;
        if(userService.loginUser(acceptJson)){
            status = HttpStatus.OK;
            message = "Login is done";
        }else{
            status = HttpStatus.NOT_ACCEPTABLE;
            message = "Login is failed";
        }

        APIResponse response = new APIResponse<>();
        response.setStatus(status);
        response.setMessage(message);
        response.setAcceptJson(acceptJson);

        return new ResponseEntity<>( response , status);

    }

    // 유저아이디를 통해서 유저의 정보를 수정
    @PatchMapping("/")
    public ResponseEntity<APIResponse> apiChangeUser(
        @RequestBody UserPatchJson acceptJson
    ){
        HttpStatus status = HttpStatus.ACCEPTED;
        userService.userUpdate(acceptJson);

        APIResponse response = new APIResponse<>();
        response.setStatus(status);
        response.setMessage("User Update is Done");
        response.setAcceptJson(acceptJson);

        return new ResponseEntity<>( response , status);

    }

    @PatchMapping("/userPw")
    public ResponseEntity<APIResponse> apiChangeUserPw(
            @RequestBody UserPwJson acceptJson
        ){
        HttpStatus status = HttpStatus.OK;
        userService.userSetPassword(acceptJson);

        APIResponse response = new APIResponse<>();
        response.setStatus(status);
        response.setMessage("User password is Changed");
        response.setAcceptJson(acceptJson);

        return new ResponseEntity<>( response , status);
    }

    // 유저 아이디를 통해서 유저를 삭제
    // for backend developer
    @DeleteMapping("/id/{userEmail}")
    public ResponseEntity<APIResponse> apiDeleteUserById(
        @PathVariable String userId
    ){
        HttpStatus status = HttpStatus.ACCEPTED;
        String message;
        if(!userService.deleteUser(userId)){
            status = HttpStatus.NOT_ACCEPTABLE;
            message = "Check User Email";
        }else{
            status = HttpStatus.NOT_ACCEPTABLE;
            message = "Delete user is Done";
        }

        APIResponse response = new APIResponse<>();
        response.setStatus(status);
        response.setMessage("Delete User is Done");
        response.setMessage(message);

        return new ResponseEntity<>( response , status);

    }

    @GetMapping("/{userId}/notifications")
    public ResponseEntity<APIResponse<List<Notification>>> apiUserNotifications(
            @PathVariable String userId
    ){
        HttpStatus status = HttpStatus.OK;

        APIResponse response = new APIResponse<>();
        response.setStatus(status);
        response.setReturnJson(notiService.notiList(userId));

        return new ResponseEntity<>(response,status);
    }

    @DeleteMapping("/{userId}/notification")
    public ResponseEntity<APIResponse> apiDelNoti(
            @PathVariable String userId
    ){
        HttpStatus status = HttpStatus.OK;

        APIResponse response = new APIResponse<>();
        response.setStatus(status);

        return new ResponseEntity<>(response,status);
    }
}
