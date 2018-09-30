package com.example.gonggu.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class ErrorModel{
    private String timeStamp;
    private HttpStatus httpStatus;
    private String message;

    @Override
    public String toString(){
        return "Error \n" +
                "Time : " + timeStamp  + "\n" +
                "Message : " + message +"\n"
                ;

    }

}
