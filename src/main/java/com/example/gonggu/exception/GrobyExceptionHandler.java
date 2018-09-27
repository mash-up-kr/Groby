package com.example.gonggu.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GrobyExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorModel> baseExceptionHandler(BaseException e){
        return new ResponseEntity<>(
                e.getErrorModel()
                ,e.getErrorModel().getHttpStatus()
        );
    }
}
