package com.example.gonggu.Controller.User;

import com.example.gonggu.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/*")
public class UserController {
    /*
     User 관련 API Controller
     @PathVariable(GET) 과 acceptJson(!GET) 을 통해서 정보를 받아온다.
     return : Optional<AcceptJson> && HttpStatus && UserResponse Class 를 Entity에 포함시켜 보낸다.
    */



    // 유저 아이디를 통해서 유저 정보를 가져온다
    @GetMapping(value = "/id/{userId}")
    public ResponseEntity<UserResponse> apiGetUserById(
            @PathVariable String userId
    ){
        UserResponse returnResponse = new UserResponse();
        HttpStatus status = HttpStatus.OK;


        returnResponse.setStatus(status);
        returnResponse.setMessage("get user is done");
        returnResponse.setAcceptJson(null);

        return new ResponseEntity<UserResponse>(returnResponse, status);
    }

    // 유저 넘버 (PK) 를 통해서 유저를 가져온다.
    @GetMapping(value = "/usernum/{userNum}")
    public ResponseEntity<UserResponse> apiGetUserByUserNum(
            @PathVariable String userNum
    ){
        UserResponse returnResponse = new UserResponse();
        HttpStatus status = HttpStatus.OK;

        User test = new User(Long.valueOf(userNum));


        returnResponse.setStatus(status);
        returnResponse.setMessage("get user is done");
        returnResponse.setAcceptJson(test);

        return new ResponseEntity<UserResponse>(returnResponse, status);
    }

    // Signup 관련 , 유저를 생성한다.
    @PostMapping("/")
    public ResponseEntity<UserResponse> apiCreateUser(
        @RequestBody Map<String,Object> acceptJson
    ){
        UserResponse returnResponse = new UserResponse();
        HttpStatus status = HttpStatus.OK;
        // can communicate with empty data like this
        // System.out.println(acceptJson.get("user_id"));


        returnResponse.setStatus(status);
        returnResponse.setMessage("get user is done");
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<UserResponse>(returnResponse, status);
    }

    // 유저 로그인
    @PostMapping("/login")
    public ResponseEntity<UserResponse> apiLoginUser(
            @RequestBody Map<String,Object> acceptJson
    ){
        UserResponse returnResponse = new UserResponse();
        HttpStatus status = HttpStatus.OK;

        returnResponse.setStatus(status);
        returnResponse.setMessage("Login is done");
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<UserResponse>(returnResponse, status);
    }

    //
    @PatchMapping("/")
    public ResponseEntity<UserResponse> apiChangeUser(
        @RequestBody Map<String,Object> acceptJson
    ){
        UserResponse returnResponse = new UserResponse();
        HttpStatus status = HttpStatus.ACCEPTED;

        returnResponse.setStatus(status);
        returnResponse.setMessage("Change User is done");
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<UserResponse>(returnResponse, status);
    }

    @DeleteMapping("/id/{userId}")
    public ResponseEntity<UserResponse> apiDeleteUserById(
        @RequestBody Map<String,Object> acceptJson
    ){
        UserResponse returnResponse = new UserResponse();
        HttpStatus status = HttpStatus.OK;

        returnResponse.setStatus(status);
        returnResponse.setMessage("Delete User is Done");
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<UserResponse>(returnResponse, status);
    }


}
