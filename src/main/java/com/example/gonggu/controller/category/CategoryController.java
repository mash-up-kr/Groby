package com.example.gonggu.controller.category;

import com.example.gonggu.controller.APIResponse;
import com.example.gonggu.domain.category.Category;
import com.example.gonggu.persistence.category.CategoryRepository;
import com.example.gonggu.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/category/*")
public class CategoryController {
    /*
     category 관련 API controller
     @PathVariable(GET) 과 acceptJson(!GET) 을 통해서 정보를 받아온다.
     받아오는 json 이 정확해야 한다.
     return : HttpStatus && CategoryResponse Class 를 Entity에 포함시켜 보낸다.
    */
    @Autowired
    CategoryService categoryService;

    //생성된 카테고리 보여주기
    @GetMapping("/")
    public ResponseEntity<APIResponse> apiGetCategory(){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        Map<Object, Object> category = categoryService.getCategory();

        returnResponse.setStatus(status);
        returnResponse.setMessage("All Category");
        returnResponse.setReturnJson(category);

        return new ResponseEntity<>(returnResponse,status);
    }

    //카테고리 생성
    @PostMapping("/")
    public ResponseEntity<APIResponse> apiCreateCategory(
            @RequestBody CategoryAcceptJson acceptJson
    ) {
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        categoryService.createCategory(acceptJson.getCategory());

        returnResponse.setStatus(status);
        returnResponse.setMessage("category create is done ");
        returnResponse.setAcceptJson(acceptJson);

        return new ResponseEntity<>(returnResponse, status);
    }

    //카테고리 수정
    @PatchMapping("/")
    public ResponseEntity<APIResponse> apiUpdateCategory(
            @RequestBody CategoryAcceptJson acceptJson
    ) {
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.ACCEPTED;

        categoryService.updatecategory(acceptJson);

        returnResponse.setStatus(status);
        returnResponse.setMessage("Change update is done");
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<>(returnResponse, status);
    }

//    //카테고리 삭제
//    @DeleteMapping("id/{categoryId}")
//    public ResponseEntity<APIResponse> apiDeleteCategory(
//            @PathVariable Long categoryId
//    ) {
//        APIResponse returnResponse = new APIResponse();
//        HttpStatus status = HttpStatus.ACCEPTED;
//
//        if(!categoryService.deletecategory(categoryId)){
//            status = HttpStatus.NOT_ACCEPTABLE;
//            returnResponse.setMessage("Check Category Id");
//        }
//
//        else{
//            returnResponse.setStatus(status);
//            returnResponse.setMessage("Delete Category is Done");
//        }
//
//        return new ResponseEntity<APIResponse>(returnResponse, status);
//    }
}
