package com.example.gonggu.controller.category;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category/*")
public class CategoryController {
    /*
     category 관련 API controller
     @PathVariable(GET) 과 acceptJson(!GET) 을 통해서 정보를 받아온다.
     받아오는 json 이 정확해야 한다.
     return : HttpStatus && CategoryResponse Class 를 Entity에 포함시켜 보낸다.
    */
    @GetMapping("")
    public void getNowCategory(){

    }
}
