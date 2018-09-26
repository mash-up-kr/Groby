package com.example.gonggu.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException{

    private ErrorModel errorModel;

    public BaseException(HttpStatus status){
        this(status,null);
    }

    public BaseException(HttpStatus status,String message){
        this.errorModel = ErrorModel.builder()
                .httpStatus(status)
                .httpStatusCode(status.value())
                .message(message).build();

    }



}
