package com.example.gonggu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    // Invalid Parameter With @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorModel> invalidDtoExHandler(MethodArgumentNotValidException ex){
        return new ResponseEntity<>(
                ErrorModel.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("정확한 파라미터를 사용해주세요")
                        .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                        .build() ,
                HttpStatus.BAD_REQUEST
        );
    }


}
