package com.example.gonggu.controller.category;

import com.example.gonggu.domain.category.Category;
import com.example.gonggu.dto.APIResponse;
import com.example.gonggu.dto.category.CategoryCreateDto;
import com.example.gonggu.dto.category.CategoryPatchDto;
import com.example.gonggu.service.category.CategoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @ApiOperation(value = "apiGetCategory",notes = "카테고리 목록 보여주기")
    @GetMapping("/")
    public ResponseEntity<APIResponse<List<Category>>> apiGetCategory(){

        APIResponse response = new APIResponse();
        HttpStatus status = HttpStatus.OK;
        List<Category> categoryList = categoryService.getCategory();

        response.setStatus(status);
        response.setMessage("All Category");
        response.setReturnJson(categoryList);

        return new ResponseEntity<>(response ,status);
    }

    //카테고리 생성
    //TODO POST 요청 실패 ... 확인하기
    @ApiOperation(value = "apiCreateCategory",notes = "새로운 카테고리 생성하기")
    @PostMapping("/")
    public void apiCreateCategory(
            @Valid @RequestBody CategoryCreateDto acceptJson
    ) {
        System.out.println(acceptJson);
        categoryService.createCategory(acceptJson.getCategory());
    }

    //카테고리 수정
    //TODO PATCH 요청 실패 ... 확인하기
    @ApiOperation(value = "apiUpdateCategory",notes = "카테고리 이름 바꾸기")
    @PatchMapping("/")
    public void apiUpdateCategory(
            @RequestBody CategoryPatchDto acceptJson
    ) {
        System.out.println(acceptJson);

//        APIResponse returnResponse = new APIResponse();
////        HttpStatus status = HttpStatus.ACCEPTED;
//        HttpStatus status = HttpStatus.OK;
//
//        categoryService.updatecategory(acceptJson);
//
//        returnResponse.setStatus(status);
//        returnResponse.setMessage("Change update is done");
//        returnResponse.setAcceptJson(acceptJson);
//        return new ResponseEntity<>(returnResponse, status);
    }

    //카테고리 삭제
    @ApiOperation(value = "apiDeleteCategory",notes = "카테고리 삭제하기")
    @DeleteMapping("/{category}")
    public ResponseEntity<APIResponse> apiDeleteCategory(
            @PathVariable String category
    ) {
        APIResponse returnResponse = new APIResponse();
//        HttpStatus status = HttpStatus.ACCEPTED;
        HttpStatus status = HttpStatus.OK;

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
