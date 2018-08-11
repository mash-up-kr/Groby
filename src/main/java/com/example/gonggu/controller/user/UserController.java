package com.example.gonggu.controller.user;

import com.example.gonggu.controller.APIResponse;
import com.example.gonggu.domain.user.User;
import com.example.gonggu.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    // 유저 아이디를 통해서 유저 정보 리턴
    @GetMapping(value = "/id/{userId}")
    public ResponseEntity<APIResponse> apiGetUserById(
            @PathVariable String userId
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        User user = userService.getUserBy(new HashMap<String,Object>(){{
            put("getUserBy" , "Id");
            put("userEmail" , userId);
        }});

        returnResponse.setStatus(status);
        returnResponse.setMessage("get user is done");
        returnResponse.setAcceptJson(null);
        returnResponse.setReturnJson(user);

        return new ResponseEntity<APIResponse>(returnResponse, status);
    }

    // 유저 넘버 (PK) 를 통해서 유저 정보 리턴
    @GetMapping(value = "/usernum/{userNum}")
    public ResponseEntity<APIResponse> apiGetUserByUserNum(
            @PathVariable String userNum
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.CREATED;

        User user = userService.getUserBy(new HashMap<String,Object>(){{
            put("getUserBy" , "userId");
            put("userId",Long.valueOf(userNum));
        }});

        returnResponse.setStatus(status);
        returnResponse.setMessage("Create user is done");
        returnResponse.setAcceptJson(null);
        returnResponse.setReturnJson(user);

        return new ResponseEntity<>(returnResponse, status);
    }

    // Signup 관련 , 유저를 생성
    @PostMapping("")
    public ResponseEntity<APIResponse> apiCreateUser(
        @RequestBody Map<String,Object> acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;
        // can communicate with empty data like this
        // System.out.println(acceptJson.get("user_id"));

        userService.createUser(acceptJson);

        returnResponse.setStatus(status);
        returnResponse.setMessage("get user is done");
        returnResponse.setAcceptJson(acceptJson);

        return new ResponseEntity<APIResponse>(returnResponse, status);
    }

    // 유저 로그인
    @PostMapping("/login")
    public ResponseEntity<APIResponse> apiLoginUser(
            @RequestBody Map<String,Object> acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status;

        if(userService.loginUser(acceptJson)){
            status = HttpStatus.OK;
            returnResponse.setMessage("Login is done");
        }else{
            status = HttpStatus.NOT_ACCEPTABLE;
            returnResponse.setMessage("Login is failed");
        }

        returnResponse.setStatus(status);
        returnResponse.setAcceptJson(acceptJson);

        returnResponse.setReturnJson(null);
        // 만약 이 부분이 없다면 어떻게 되는걸까??
        return new ResponseEntity<APIResponse>(returnResponse, status);
    }

    // 유저아이디를 통해서 유저의 정보를 수정
    @PatchMapping("")
    public ResponseEntity<APIResponse> apiChangeUser(
        @RequestBody Map<String,Object> acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.ACCEPTED;

        userService.userUpdate(acceptJson);

        returnResponse.setStatus(status);
        returnResponse.setMessage("Change user is done");
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<APIResponse>(returnResponse, status);
    }

    // 유저 아이디를 통해서 유저를 삭제
    @DeleteMapping("/id/{userId}")
    public ResponseEntity<APIResponse> apiDeleteUserById(
        @PathVariable String userId
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        if(!userService.deleteUser(userId)){
            status = HttpStatus.NOT_ACCEPTABLE;
            returnResponse.setMessage("Check User Id");
        }else{
            returnResponse.setStatus(status);
            returnResponse.setMessage("Delete user is Done");
        }

        return new ResponseEntity<APIResponse>(returnResponse, status);
    }


}
