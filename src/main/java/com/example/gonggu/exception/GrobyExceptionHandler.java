package com.example.gonggu.exception;

import com.example.gonggu.config.NowTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GrobyExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorModel> baseExceptionHandler(BaseException e){
        ErrorModel model =
                ErrorModel.builder()
                .httpStatus(e.getStatus())
                .message(e.getCustomMessage())
                .timeStamp(NowTime.builder().build().toString())
                .build();

        return new ResponseEntity<>( model , e.getStatus() );
    }

    // Invalid Parameter With @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorModel> invalidDtoExHandler(MethodArgumentNotValidException ex){
        return new ResponseEntity<>(
                ErrorModel.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("정확한 파라미터를 사용해주세요")
                        .build() ,
                HttpStatus.BAD_REQUEST
        );
    }


}
