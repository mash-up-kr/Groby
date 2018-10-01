package com.example.gonggu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException{
    private final static HttpStatus status = HttpStatus.NOT_FOUND;

    public NotFoundException(){
        super(status,"Bad Request");
    }

    public NotFoundException(String message){ super(status,message); }
}
