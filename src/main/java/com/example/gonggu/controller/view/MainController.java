package com.example.gonggu.controller.view;

import com.example.gonggu.controller.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/*")
public class MainController {
    /*
    Home View 에 필요한 API
    @PathVariable(GET) 과 acceptJson(!GET) 을 통해서 정보를 받아온다.
     받아오는 json 이 정확해야 한다.
    */

    @GetMapping("/home")
    public ResponseEntity<APIResponse> apiGetHome(){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        // 최신글 + 핫아이템

        returnResponse.setStatus(status);
        returnResponse.setMessage("home view Info \n recent view + a hot item");
        returnResponse.setReturnJson(null);

        return new ResponseEntity<>(returnResponse, status);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<APIResponse> apiGetCategoryItem(){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        // 카테고리 아이템 검색

        returnResponse.setStatus(status);
        returnResponse.setMessage("return Category Item");
        returnResponse.setReturnJson(null);

        return new ResponseEntity<>(returnResponse, status);
    }


}
