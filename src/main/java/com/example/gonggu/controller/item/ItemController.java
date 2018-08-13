package com.example.gonggu.controller.item;

import com.example.gonggu.controller.APIResponse;
import com.example.gonggu.service.item.ItemService;
import jdk.nashorn.internal.objects.annotations.Getter;
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

    // item id 를 통해서 아이템을 찾는다.
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

    // 아이템을 수정
    @PatchMapping("/{itemId}")
    public ResponseEntity<APIResponse> apiChangeItem(
            @PathVariable String itemId,
            @RequestBody Map<String,Object> acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.ACCEPTED;

        // function

        returnResponse.setStatus(status);
        returnResponse.setMessage("Item Changed");
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<>(returnResponse,status);

    }

    // 아이템과 탭을 생성
    @PostMapping("/")
    public ResponseEntity<APIResponse> apiCreateItem(
            @RequestBody ItemAcceptJson acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.CREATED;

        // create item , all tabs ( empty )

        returnResponse.setStatus(status);
        returnResponse.setMessage("Create Item");
        returnResponse.setAcceptJson(acceptJson);

        return new ResponseEntity<>(returnResponse,status);
    }

    // 탭 수정
    // 다음 단계로 넘어가는 작업들이 여기서 진행이 된다.
    @PatchMapping("/tab")
    public ResponseEntity<APIResponse> apiUpdateTab(
            @RequestBody ItemAcceptJson acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.ACCEPTED;

        // update tab
        switch (acceptJson.getA_TabNumber()){
            case "2" :
                System.out.println("2");
                break;
            case "3" :
                System.out.println("3");
                break;
            case "4" :
                System.out.println("2");
                break;
            case "5" :
                System.out.println("2");
                break;

        }

        returnResponse.setStatus(status);
        returnResponse.setMessage("Update Tab");
        returnResponse.setAcceptJson(acceptJson);

        return new ResponseEntity<>(returnResponse,status);
    }

    // t1 지우기 ?
    @DeleteMapping("/{itemId}")
    public ResponseEntity<APIResponse> apiDeleteItem(
            @PathVariable String itemId
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.ACCEPTED;

        // function

        returnResponse.setStatus(status);
        returnResponse.setMessage("Item Is Deleted");
//        returnResponse.setReturnJson(null);
        return new ResponseEntity<>(returnResponse,status);
    }

    // t2 유저의 참여
    @PostMapping("/{itemId}/userlist")
    public ResponseEntity<APIResponse> apiJoinUserList(
            @PathVariable String itemId,
            @RequestBody Map<String,Object> acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.CREATED;

        //

        returnResponse.setStatus(status);
        returnResponse.setMessage("Join User List Is Done");
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<>(returnResponse,status);
    }

    // t2 참여 유저 목록
    @GetMapping("/{itemId}/userlist")
    public ResponseEntity<APIResponse> apiGetUserList(
            @PathVariable String itemId
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.CREATED;

        //

        returnResponse.setStatus(status);
        returnResponse.setMessage("Get User List");
        returnResponse.setReturnJson(null);

        return new ResponseEntity<>(returnResponse,status);
    }


    // t2 참여 유저 목록 수정
    @PatchMapping("/{itemId}/userlist")
    public ResponseEntity<APIResponse> apiChangeUserList(
            @PathVariable String itemId,
            @RequestBody Map<String,Object> acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.ACCEPTED;

        // action 이라는 값을 줘서 삭제 확인 보류를 나누자

        returnResponse.setStatus(status);
        returnResponse.setMessage("Change User List Is Done");
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<>(returnResponse,status);
    }

    // t2 사용자의 옵션 확인
    @GetMapping("/{itemId}/optionstring")
    public ResponseEntity<APIResponse> apiGetOptionString(
            @PathVariable String itemId
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        //

        returnResponse.setStatus(status);
        returnResponse.setMessage("Get Option String");
        returnResponse.setReturnJson(null);

        return new ResponseEntity<>(returnResponse,status);

    }

    // t3 , t4 업뎃만 하면 완료

    // t5
    @GetMapping("/{itemId}/userlist/{userEmail}")
    public ResponseEntity<APIResponse> apiUserListSearch(
            @PathVariable(name = "itemId") String itemId,
            @PathVariable(name = "userEmail") String userEmail
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        // 유저 검색

        returnResponse.setStatus(status);
        returnResponse.setMessage("User Search Is Done");
        returnResponse.setReturnJson(null);
        return new ResponseEntity<>(returnResponse,status);
    }




}
