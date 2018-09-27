package com.example.gonggu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends BaseException{

    private final static HttpStatus status = HttpStatus.CONFLICT;

    public AlreadyExistsException(){
        super(status,"Bad Request");
    }

    public AlreadyExistsException(String message){
        super(status,message);
    }

}
