package com.example.gonggu.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException{

    private HttpStatus status;
    private String customMessage;

    public BaseException(HttpStatus status){
        this(status,null);
    }

    public BaseException(HttpStatus status,String message){
        this.status = status;
        this.customMessage = message;
    }



}
