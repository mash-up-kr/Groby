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
            @RequestPart(value = "files") MultipartFile[] files
    ) {
        APIResponse<List<String>> returnResponse = new APIResponse<List<String>>();
        HttpStatus status = HttpStatus.OK;

        returnResponse.setReturnJson(s3Service.uploadFile(files));
        returnResponse.setStatus(status);
        returnResponse.setMessage("Item Information");

        return new ResponseEntity<>(returnResponse,status);
    }



    // 아이템과 탭을 생성
    @ApiOperation(value = "apiCreateItem",notes = "아이템 생성 , Date : 0000-00-00")
    @PostMapping("/")
    public void apiCreateItem(
            @Valid @RequestBody ItemCreateJson acceptJson,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) throw new BadRequestException("필수 파라미터를 채워주세요");
        itemService.createItem(acceptJson);
    }

    // 탭 수정
    // 다음 단계로 넘어가는 작업들이 여기서 진행이 된다.
    @ApiOperation(value = "apiUpdateTab",notes = "editTab:false -> 다음 단계로 넘어갈 경우 / editTab:true -> 해당 단계의 내용 수정 // 필수 파라미터 :itemId, writerId, editTab, category, itemTitle, targetTab, imgPathList, itemAmountLimit //수정하거나 추가하는 Tab들의 json 필수적으로 채우기 // optionString EX ) 사이즈>L:100,M:0,S:-100/색상>빨:1000,파:1000,흰:1000/배송>산간:100,서울:0,집근처:-100")
    @PatchMapping("/tab")
    public void apiUpdateTab(
            @Valid @RequestBody ItemPatchJson acceptJson,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) throw new BadRequestException("필수 파라미터를 채워주세요");
        itemService.patchItemTab(acceptJson);
    }

    // delete
    @ApiOperation(value = "apiDeleteItem",notes = "아이템 삭제")
    @DeleteMapping("/{itemId}")
    public void apiDeleteItem(
            @PathVariable String itemId
    ){
        itemService.deleteItem(itemId);
    }

    // t1 like
    @ApiOperation(value = "apiToggleLike",notes = "아이템 사용자가 눌렀을 경우 -> 자동으로 좋아요/취소 토글 가능")
    @PostMapping("/like")
    public ResponseEntity<APIResponse> apiToggleLike(
            @Valid @RequestBody ItemLikeJson acceptJson
    ){
        APIResponse returnResponse = new APIResponse();
//        HttpStatus status = HttpStatus.ACCEPTED;
        HttpStatus status = HttpStatus.OK;

        if(itemService.toggleLike(acceptJson))
            returnResponse.setMessage("Add Like");
        else {
            status = HttpStatus.NOT_ACCEPTABLE;
            returnResponse.setMessage("Remove Like");
        }

        returnResponse.setStatus(status);
        returnResponse.setAcceptJson(acceptJson);
        return new ResponseEntity<>(returnResponse,status);
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
    @PostMapping("/userlist")
    public void apiJoinUserList(
            @Valid @RequestBody ItemJoinAcceptJson acceptJson,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) throw new BadRequestException("필수 파라미터를 채워주세요");
        itemService.insertParticipantUser(acceptJson);
    }

    // t2 참여 유저 목록 수정
    @ApiOperation(value = "apiChangeUserPermission" , notes = "status > Tab2[ 0:default / 1:승인 / 2:보류 / 3:취소 ] / Tab5[ 11:배부완료 / 12:배부안됨 ]" )
    @PatchMapping("/userlist")
    public void apiChangeUserPermission(
            @Valid @RequestBody ParticipantListUserPermission acceptJson,
            BindingResult bindingResult
    ){
        if(bindingResult == null) throw new BadRequestException("필수 파라미터를 채워주세요");
        itemService.changeUserPermission(acceptJson);
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
