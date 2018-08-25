package com.example.gonggu.controller.category;

import com.example.gonggu.domain.category.Category;
import com.example.gonggu.dto.APIResponse;
import com.example.gonggu.dto.category.CategoryCreateJson;
import com.example.gonggu.dto.category.CategoryPatchJson;
import com.example.gonggu.service.category.CategoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<APIResponse<List<Category>>> apiGetCategory(){

        APIResponse response = new APIResponse();
        HttpStatus status = HttpStatus.OK;
        List<Category> categoryList = categoryService.getCategory();

        response.setStatus(status);
        response.setMessage("All Category");

        return new ResponseEntity<>(response ,status);
    }

    //카테고리 생성
    @PostMapping("/")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", value = "생성할카테고리", required = false, dataType = "string", paramType = "path", defaultValue = "")
    })
    public ResponseEntity<APIResponse> apiCreateCategory(
            @RequestBody CategoryCreateJson acceptJson
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
            @RequestBody CategoryPatchJson acceptJson
    ) {
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.ACCEPTED;

        categoryService.updatecategory(acceptJson);

        returnResponse.setStatus(status);
        returnResponse.setMessage("Change update is done");
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<>(returnResponse, status);
    }

    //카테고리 삭제
    @DeleteMapping("{category}")
    public ResponseEntity<APIResponse> apiDeleteCategory(
            @PathVariable String category
    ) {
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.ACCEPTED;

        if(!categoryService.deletecategory(category)){
            status = HttpStatus.NOT_ACCEPTABLE;
            returnResponse.setMessage("Check Category Id");
        } else{
            status = HttpStatus.OK;
            returnResponse.setMessage("Delete Category is Done");
        }

        returnResponse.setStatus(status);
        return new ResponseEntity<APIResponse>(returnResponse, status);
    }
}
