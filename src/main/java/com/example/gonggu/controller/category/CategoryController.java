package com.example.gonggu.controller.category;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category/*")
public class CategoryController {
    /*
     category 관련 API controller
     @PathVariable(GET) 과 acceptJson(!GET) 을 통해서 정보를 받아온다.
     받아오는 json 이 정확해야 한다.
     return : HttpStatus && CategoryResponse Class 를 Entity에 포함시켜 보낸다.
    */
    @GetMapping("/")
    public void getNowCategory(){
        System.out.println("wow");
    }
    @PostMapping("/")
    public String wowo(){

        return "hello world";
    }
    @PatchMapping("/")
    public void wowo2(){
        System.out.println("wow2");
    }

}
