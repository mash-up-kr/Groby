package com.example.gonggu.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException{

    private final static HttpStatus status = HttpStatus.BAD_REQUEST;

    public BadRequestException(){
        super(status,"Bad Request");
    }

    public BadRequestException(String message){
        super(status,message);
    }

}
