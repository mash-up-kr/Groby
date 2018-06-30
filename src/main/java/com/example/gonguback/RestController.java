package com.example.gonguback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.tags.Param;

@org.springframework.web.bind.annotation.RestController

public class RestController {
    @GetMapping("/hello")

    public String hello(){
        return "Hello world";
    }

    @GetMapping("/user")
    public User userInfo(){
        User user = new User();
        user.setUser_num(1);
        user.setUser_id("a");
        user.setUser_pw("111");
        user.setUser_name("소연");
        user.setUser_email("abc@abc.com");
        user.setAccount_num("111-111");
        user.setAccount_bank("신한");
        user.setAccount_holder("소연");
        return user;
    }

    @PostMapping("/user")
    public User userInfo(@RequestBody User user){
        
        System.out.println(user.getUser_name());
        return user;
    }


}
