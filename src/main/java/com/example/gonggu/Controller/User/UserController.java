package com.example.gonggu.Controller.User;

import com.example.gonggu.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/*")
public class UserController {


    @GetMapping(value = "/id/{userId}")
    public ResponseEntity<User> apiGetUserById(
            @PathVariable String userId
    ){
        User test = new User(userId);

        return new ResponseEntity<User>( test , HttpStatus.OK);
    }

    @GetMapping(value = "/usernum/{userNum}")
    public ResponseEntity<User> apiGetUserByUserNum(
            @PathVariable String userNum
    ){

        User test = new User(Long.valueOf(userNum));

        return new ResponseEntity<User>( test , HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Map> poTest(
        @RequestBody Map<String,Object> acceptJson
    ){
        // can communicate with empty data like this
        // System.out.println(acceptJson.get("user_id"));

        acceptJson.put("message","Change User is Done");

        return new ResponseEntity<Map>( acceptJson , HttpStatus.CREATED );
    }

    @PatchMapping("/")
    public ResponseEntity<Map> apiChangeUserAccount(
        @RequestBody Map<String,Object> acceptJson
    ){
        acceptJson.put("message","Change User is Done");

        return new ResponseEntity<Map>(acceptJson,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/id/{userId}")
    public ResponseEntity<Map> apiDeleteUserById(
        @RequestBody Map<String,Object> acceptJson
    ){
        acceptJson.put("message","Delete User is Done");
        return new ResponseEntity<Map>(acceptJson,HttpStatus.ACCEPTED);
    }
}
