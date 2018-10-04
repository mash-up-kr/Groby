package com.example.gonggu.controller.item;

import com.example.gonggu.domain.item.ListOfParticipantForItem;
import com.example.gonggu.dto.APIResponse;
import com.example.gonggu.dto.item.*;
import com.example.gonggu.exception.BadRequestException;
import com.example.gonggu.service.item.ItemService;
import com.example.gonggu.service.item.S3Service;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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

    @Autowired
    S3Service s3Service;

    // item id 를 통해서 아이템을 찾는다.
    @ApiOperation(value = "apiGetItem",notes = "아이템 넘버를 통해 아이템 정보 가져오기")
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

    /*
    아이템의 각 탭에 해당하는 사진을 저장하는 API
     */
    @ApiOperation(value = "apiStorageImg", notes = "아이템에 해당하는 이미지를 저장")
    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse<List<String>>> uploadFile(
            @RequestPart(value = "file") MultipartFile[] file
    ) {
        APIResponse<List<String>> returnResponse = new APIResponse<List<String>>();
        HttpStatus status = HttpStatus.OK;

        returnResponse.setReturnJson(s3Service.uploadFile(file));
        returnResponse.setStatus(status);
        returnResponse.setMessage("Item Information");

        return new ResponseEntity<>(returnResponse,status);
    }



    // 아이템과 탭을 생성
    @ApiOperation(value = "apiCreateItem",notes = "아이템 생성 , Date : 0000-00-00")
    @PostMapping("/")
    public ResponseEntity<APIResponse> apiCreateItem(
            @RequestBody ItemCreateJson acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status;

        if(itemService.createItem(acceptJson)){
//            status = HttpStatus.CREATED;
            status = HttpStatus.OK;
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
    @ApiOperation(value = "apiUpdateTab",notes = "editTab:false -> 다음 단계로 넘어갈 경우 / editTab:true -> 해당 단계의 내용 수정 // 수정하거나 추가하는 Tab들의 json 필수적으로 채워야함 // optionString EX ) 사이즈>L:100,M:0,S:-100/색상>빨:1000,파:1000,흰:1000/배송>산간:100,서울:0,집근처:-100")
    @PatchMapping("/tab")
    public ResponseEntity<APIResponse> apiUpdateTab(
            @RequestBody ItemPatchJson acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status;

        // update tab
        if(itemService.patchItemTab(acceptJson)){
//            status = HttpStatus.ACCEPTED;
            status = HttpStatus.OK;
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
    @ApiOperation(value = "apiDeleteItem",notes = "아이템 삭제")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<APIResponse> apiDeleteItem(
            @PathVariable String itemId
    ){
        APIResponse returnResponse = new APIResponse();
//        HttpStatus status = HttpStatus.ACCEPTED;
        HttpStatus status = HttpStatus.OK;

        itemService.deleteItem(itemId);

        returnResponse.setStatus(status);
        returnResponse.setMessage("Item Is Deleted");
        return new ResponseEntity<>(returnResponse,status);
    }

    // t1 like
    @ApiOperation(value = "apiToggleLike",notes = "아이템 사용자가 눌렀을 경우 -> 자동으로 좋아요/취소 토글 가능")
    @PostMapping("/like")
    public void apiToggleLike(
            @Valid @RequestBody ItemLikeJson acceptJson ,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) throw new BadRequestException();

        itemService.toggleLike(acceptJson);
    }

    // t2 참여 유저 목록
    @ApiOperation(value = "apiGetUserList",notes = "tab 2 참여하는 유저 목록")
    @GetMapping("/{itemId}/userlist")
    public ResponseEntity<APIResponse<List<ListOfParticipantForItem>>> apiGetUserList(
            @PathVariable String itemId
    ){
        APIResponse returnResponse = new APIResponse();
        HttpStatus status = HttpStatus.OK;

        returnResponse.setStatus(status);
        returnResponse.setMessage("Get User List");
        returnResponse.setReturnJson(itemService.getParticipantUser(itemId));

        return new ResponseEntity<>(returnResponse,status);
    }

    // t2 유저의 참여
    @ApiOperation(value = "apiJoinUserList",notes = "tab 2 사용자 참여하기 // 옵션:수량/...>총금액 EX) L 빨:2/S 파:2>200")
    @PostMapping("/{itemId}/userlist")
    public ResponseEntity<APIResponse> apiJoinUserList(
            @PathVariable String itemId,
            @RequestBody ItemJoinAcceptJson acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
//        HttpStatus status = HttpStatus.CREATED;
        HttpStatus status = HttpStatus.OK;

        if(itemService.insertParticipantUser(itemId,acceptJson))
            returnResponse.setMessage("Join User List Is Done");
        else {
            status = HttpStatus.NOT_ACCEPTABLE;
            returnResponse.setMessage("Join User List Is Failed");
        }

        returnResponse.setStatus(status);
        return new ResponseEntity<>(returnResponse,status);
    }

    // t2 참여 유저 목록 수정
    @ApiOperation(value = "apiChangeUserPermission" , notes = "status > Tab2[ 0:default / 1:승인 / 2:보류 / 3:취소 ] / Tab5[ 11:배부완료 / 12:배부안됨 ]" )
    @PatchMapping("/{itemId}/userlist")
    public ResponseEntity<APIResponse> apiChangeUserPermission(
            @PathVariable String itemId,
            @RequestBody ParticipantListUserPermission acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
//        HttpStatus status = HttpStatus.ACCEPTED;
        HttpStatus status = HttpStatus.OK;

        itemService.changeUserPermission(acceptJson);

        returnResponse.setStatus(status);
        returnResponse.setMessage("Change User List Is Done");
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<>(returnResponse,status);
    }

    // t2 사용자의 옵션 확인
    @ApiOperation(value = "apiGetOptionString",notes = "tab 2 옵션값 처리")
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
