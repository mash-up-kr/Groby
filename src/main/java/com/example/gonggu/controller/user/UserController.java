package com.example.gonggu.controller.user;

import com.example.gonggu.domain.user.Notification;
import com.example.gonggu.dto.APIResponse;
import com.example.gonggu.dto.user.*;
import com.example.gonggu.exception.AlreadyExistsException;
import com.example.gonggu.exception.BadRequestException;
import com.example.gonggu.service.user.NotiService;
import com.example.gonggu.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user/*")
public class UserController {
    /*
     user 관련 API controller
     @PathVariable(GET) 과 acceptJson(!GET) 을 통해서 정보를 받아온다.
     받아오는 json 이 정확해야 한다.
     return : HttpStatus && APIResponse Class 를 Entity에 포함시켜 보낸다.
    */


    @Autowired
    UserService userService;
    @Autowired
    NotiService notiService;

    // 유저 아이디를 통해서 유저 정보 리턴
    @ApiOperation(value = "apiGetUserByEmail",notes = "유저 아이디(이메일)를 통해서 유저 정보 리턴")
    @GetMapping(value = "/email/{userEmail}")
    public ResponseEntity<APIResponse<UserInfo>> apiGetUserByEmail(
            @PathVariable String userEmail
    ){

        HttpStatus status = HttpStatus.OK;
        APIResponse<UserInfo> response = new APIResponse<>();
        response.setStatus(status);
        response.setMessage("get user is done");
        response.setReturnJson(
                userService.getUserBy(
                        new HashMap<String,Object>(){{
                            put("getUserBy" , "Email");
                            put("userEmail" , userEmail);
                        }})
        );

        return new ResponseEntity<>(response, status);
    }

    // 유저 넘버 (PK) 를 통해서 유저 정보 리턴
    @ApiOperation(value = "apiGetUserByUserNum",notes = "유저 넘버 (PK) 를 통해서 유저 정보 리턴")
    @GetMapping(value = "/usernum/{userNum}")
    public ResponseEntity<APIResponse<UserInfo>> apiGetUserByUserNum(
            @PathVariable String userNum
    ){

        HttpStatus status = HttpStatus.OK;
        APIResponse<UserInfo> response = new APIResponse<>();
        response.setStatus(status);
        response.setMessage("Create user is done");
        response.setReturnJson(
                userService.getUserBy(new HashMap<String,Object>(){{
                    put("getUserBy" , "userId");
                    put("userId",Long.valueOf(userNum));
                }})
        );

        return new ResponseEntity<>( response , status);
    }

    @ApiOperation(value = "apiCheckEmail",notes = "회원가입시 이메일 인증 / 중복시 에러")
    @GetMapping("/{userEmail}/check")
    public ResponseEntity<APIResponse<UserCheckEmail>> apiCheckEmail(
            @PathVariable String userEmail
    ){

        HttpStatus status = HttpStatus.OK;
        String message;
        // 이메일 체크
        // 인증이 되는 경우 이메일을 보내준다.
        // 인증번호를 보내준다.
        APIResponse response = new APIResponse<>();
        UserCheckEmail userCheckEmail = new UserCheckEmail();

//        System.out.println(URLDecoder.decode(userEmail));
        try {
            System.out.println(URLDecoder.decode(userEmail,"utf-8"));
        }catch (Exception e ){
            System.out.println(e);
        }

        if(userService.checkEmail(URLDecoder.decode(userEmail))){
            status = HttpStatus.OK;
            message = "이메일 인증 전송 성공";
            userCheckEmail.setAuthenticationNumber(userService.sendMail(userEmail));
        }else
            throw new AlreadyExistsException("중복된 이메일 입니다.");


        response.setStatus(status);
        response.setMessage(message);
        response.setReturnJson(userCheckEmail);
        return new ResponseEntity<>( response , status);
    }

    // Signup 관련 , 유저를 생성
    @ApiOperation(value = "apiCreateUser",notes = "유저 회원가입")
    @PostMapping("/")
    public void apiCreateUser(
        @Valid @RequestBody UserSignupJson acceptJson,
        BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) throw new BadRequestException("필수 파라미터를 채워주세요");
        userService.createUser(acceptJson);
    }

    // 유저 로그인
    @ApiOperation(value = "apiLoginUser",notes = "유저 로그인")
    @PostMapping("/login")
    public ResponseEntity<APIResponse<UserInfo>> apiLoginUser(
            @Valid @RequestBody UserLoginJson acceptJson ,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) throw new BadRequestException("로그인을 위한 필수 파라미터가 잘못 되었습니다.");

        UserInfo result = userService.loginUser(acceptJson);

        if(result == null)
            throw new BadRequestException("유저의 아이디 혹은 비밀번호가 잘못 되었습니다.");

        HttpStatus status = HttpStatus.OK;
        APIResponse<UserInfo> response = new APIResponse<>();
        response.setStatus(status);
        response.setMessage("get user is done");
        response.setReturnJson(result);

        return new ResponseEntity<>(response , status);

    }

    // 유저아이디를 통해서 유저의 정보를 수정
    @ApiOperation(value = "apiChangeUser",notes = "유저 아이디(이메일)를 통해서 유저의 정보를 수정 / 바뀌는 부분만 작성할 것")
    @PatchMapping("/")
    public void apiChangeUser(
        @Valid @RequestBody UserPatchJson acceptJson ,
        BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) throw new BadRequestException("유저 이메일은 필수입니다.");
        userService.userUpdate(acceptJson);
    }

    @ApiOperation(value = "apiChangeUserPw",notes = "비밀번호 변경")
    @PatchMapping("/userPw")
    public void apiChangeUserPw(
            @Valid @RequestBody UserPwJson acceptJson ,
            BindingResult bindingResult
        ){
        if(bindingResult.hasErrors()) throw new BadRequestException();

        userService.userSetPassword(acceptJson);

    }

    // 유저 아이디를 통해서 유저를 삭제
    // for backend developer
    @ApiOperation(value = "apiDeleteUserById",notes = "유저 넘버(PK)를 통해서 유저를 삭제 / isDeleted => True")
    @DeleteMapping("/id/{userId}")
    public void apiDeleteUserById(
        @PathVariable String userId
    ){
        userService.serviceDeleteUser(userId);
    }

    @ApiOperation(value = "apiGetParticipantInfo" , notes = "작성한 혹은 참여한 아이템의 목록 / 마지막 owner => 작성한 목록 t / 참여한 목록 f ")
    @GetMapping("/{userId}/participantlist/{owner}")
    public ResponseEntity<APIResponse<ReItemListDto>> apiGetParticipantInfo(
            @PathVariable(name = "userId") String userId,
            @PathVariable(name = "owner") String owner
    ){
        HttpStatus status = HttpStatus.OK;
        APIResponse response = new APIResponse();
        response.setStatus(status);
        response.setReturnJson(
                ReItemListDto.builder()
                        .ItemList(userService.getUserParticipantList(owner.equals("t")?true : false , userId))
                        .build()
        );

        return new ResponseEntity<>(response,status);
    }


    @ApiOperation(value = "apiUserNotifications",notes = "사용자 알림서비스")
    @GetMapping("/{userId}/notifications")
    public ResponseEntity<APIResponse<List<Notification>>> apiUserNotifications(
            @PathVariable String userId
    ){
        HttpStatus status = HttpStatus.OK;

        APIResponse response = new APIResponse<>();
        response.setStatus(status);
        response.setReturnJson(notiService.notiList(userId));

        return new ResponseEntity<>(response,status);
    }

    @ApiOperation(value = "apiDelNoti",notes = "사용자 알림서비스 삭제")
    @DeleteMapping("/notification/{notiId}")
    public void apiDelNoti(
            @PathVariable(name = "notiId") String notiId
    ){
        notiService.delNoti(notiId);
    }

}
