package com.example.gonggu.Controller;

import com.example.gonggu.domain.user.User;
import com.example.gonggu.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepo;


    @GetMapping("/utest")
    public @ResponseBody String utest(){
        return "hello utest";
    }

    @GetMapping("/user")
    public List<User> getUser(){
        return userRepo.findAll();
    }

    @PostMapping("/user")
    public String postUser(){
        userRepo.save(new User("onemoon1"));
        return "save okay!";
    }
}
