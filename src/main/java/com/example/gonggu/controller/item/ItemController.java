package com.example.gonggu.controller.item;

import com.example.gonggu.domain.item.ListOfParticipantForItem;
import com.example.gonggu.dto.APIResponse;
import com.example.gonggu.dto.item.*;
import com.example.gonggu.service.item.ItemInfoJson;
import com.example.gonggu.service.item.ItemService;
import com.example.gonggu.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<APIResponse<ItemInfo>> apiGetItem(
            @PathVariable String itemId
    ){
        APIResponse<ItemInfo> returnResponse = new APIResponse<ItemInfo>();
        HttpStatus status = HttpStatus.OK;

        returnResponse.setReturnJson(itemService.getItemDetail(itemId));
        returnResponse.setStatus(status);
        returnResponse.setMessage("Item Information");

        return new ResponseEntity<>(returnResponse,status);
    }

    // 아이템과 탭을 생성
    @PostMapping("/")
    public ResponseEntity<APIResponse> apiCreateItem(
            @RequestBody ItemCreateJson acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status;

        if(itemService.createItem(acceptJson)){
            status = HttpStatus.CREATED;
            returnResponse.setMessage("Create Item");
        }else{
            status = HttpStatus.NOT_ACCEPTABLE;
            returnResponse.setMessage("Create Item Is Failed");
        }

        returnResponse.setStatus(status);
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<>(returnResponse,status);
    }

    // 탭 수정
    // 다음 단계로 넘어가는 작업들이 여기서 진행이 된다.
    @PatchMapping("/tab")
    public ResponseEntity<APIResponse> apiUpdateTab(
            @RequestBody ItemPatchJson acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status;

        // update tab
        if(itemService.patchItemTab(acceptJson)){
            status = HttpStatus.ACCEPTED;
            returnResponse.setMessage("Update Tab Is Done");
        }else{
            status = HttpStatus.NOT_ACCEPTABLE;
            returnResponse.setMessage("Update Tab Is Failed");
        }

        returnResponse.setStatus(status);
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<>(returnResponse,status);
    }

    // delete
    @DeleteMapping("/{itemId}")
    public ResponseEntity<APIResponse> apiDeleteItem(
            @PathVariable String itemId
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.ACCEPTED;

        itemService.deleteItem(itemId);

        returnResponse.setStatus(status);
        returnResponse.setMessage("Item Is Deleted");
        return new ResponseEntity<>(returnResponse,status);
    }

    // t1 like
    @PostMapping("/like")
    public ResponseEntity<APIResponse> apiAddLike(
            @RequestBody ItemLikeJson acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.ACCEPTED;

        if(itemService.toggleLike(acceptJson))
            returnResponse.setMessage("Add Like");
        else
            returnResponse.setMessage("Remove Like");


        returnResponse.setStatus(status);
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<>(returnResponse,status);
    }

    // t2 참여 유저 목록
    @GetMapping("/{itemId}/userlist")
    public ResponseEntity<APIResponse<List<ListOfParticipantForItem>>> apiGetUserList(
            @PathVariable String itemId
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.CREATED;

        returnResponse.setStatus(status);
        returnResponse.setMessage("Get User List");
        returnResponse.setReturnJson(itemService.getParticipantUser(itemId));

        return new ResponseEntity<>(returnResponse,status);
    }

    // t2 유저의 참여
    @PostMapping("/{itemId}/userlist")
    public ResponseEntity<APIResponse> apiJoinUserList(
            @PathVariable String itemId,
            @RequestBody ItemJoinAcceptJson acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.CREATED;

        if(itemService.insertParticipantUser(itemId,acceptJson))
            returnResponse.setMessage("Join User List Is Done");
        else
            returnResponse.setMessage("Join User List Is Failed");

        returnResponse.setStatus(status);
        return new ResponseEntity<>(returnResponse,status);
    }

    // t2 참여 유저 목록 수정
    @ApiOperation(value = "apiChangeUserPermission" , notes = "status > \n Tab2[ 0:default / 1:승인 / 2:보류 / 3:취소 ] \n/ Tab5[ 11:배부완료 / 12:배부안됨 ]" )
    @PatchMapping("/{itemId}/userlist")
    public ResponseEntity<APIResponse> apiChangeUserPermission(
            @PathVariable String itemId,
            @RequestBody ParticipantListUserPermission acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.ACCEPTED;

        itemService.changeUserPermission(acceptJson);

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

        returnResponse.setStatus(status);
        returnResponse.setMessage("Get Option String");
        returnResponse.setReturnJson(itemService.getOptionInfo(itemId));

        return new ResponseEntity<>(returnResponse,status);

    }

}
