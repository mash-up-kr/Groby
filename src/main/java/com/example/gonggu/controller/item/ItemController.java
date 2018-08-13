package com.example.gonggu.controller.item;

import com.example.gonggu.controller.APIResponse;
import com.example.gonggu.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/item/*")
public class ItemController {
    /*
     item 관련 API controller
     @PathVariable(GET) 과 acceptJson(!GET) 을 통해서 정보를 받아온다.
     받아오는 json 이 정확해야 한다.
     return : HttpStatus && APIResponse Class 를 Entity에 포함시켜 보낸다.
    */

    @Autowired
    ItemService itemService;

    @GetMapping("/{itemId}")
    public ResponseEntity<APIResponse> apiGetItem(
            @PathVariable String itemId
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        // function

        returnResponse.setStatus(status);
        returnResponse.setMessage("Item Information");
//        returnResponse.setReturnJson(null);
        return new ResponseEntity<>(returnResponse,status);

    }

    @PostMapping("")
    public ResponseEntity<APIResponse> apiCreateItemAndTabs(
            @RequestBody Map<String,Object> acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        // create item , tabs

        returnResponse.setStatus(status);
        returnResponse.setMessage("Create Item and Tabs");
        returnResponse.setAcceptJson(acceptJson);

        return new ResponseEntity<>(returnResponse,status);
    }

    @PostMapping("/2")
    public ResponseEntity<APIResponse> apiUpdateTab2(
            @RequestBody Map<String,Object> acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        //  tab2

        returnResponse.setStatus(status);
        returnResponse.setMessage("Update Tab2");
        returnResponse.setAcceptJson(acceptJson);

        return new ResponseEntity<>(returnResponse,status);
    }

    @PostMapping("/3")
    public ResponseEntity<APIResponse> apiUpdateTab3(
            @RequestBody Map<String,Object> acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        // tab3

        returnResponse.setStatus(status);
        returnResponse.setMessage("Update Tab3");
        returnResponse.setAcceptJson(acceptJson);

        return new ResponseEntity<>(returnResponse,status);
    }

    @PostMapping("/4")
    public ResponseEntity<APIResponse> apiUpdateTab4(
            @RequestBody Map<String,Object> acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        // process tab4

        returnResponse.setStatus(status);
        returnResponse.setMessage("Update Tab4");
        returnResponse.setAcceptJson(acceptJson);

        return new ResponseEntity<>(returnResponse,status);
    }

    @PostMapping("/5")
    public ResponseEntity<APIResponse> apiUpdateTab5(
            @RequestBody Map<String,Object> acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        // create tab2

        returnResponse.setStatus(status);
        returnResponse.setMessage("Update Tab5");
        returnResponse.setAcceptJson(acceptJson);

        return new ResponseEntity<>(returnResponse,status);
    }



}
