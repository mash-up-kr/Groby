package com.example.gonggu.exception;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Builder
@Data
public class ErrorModel{
    @CreationTimestamp
    Timestamp nowStamp;
    private HttpStatus httpStatus;
    private int httpStatusCode;
    private String message;

    @Override
    public String toString(){
        return "Error \n" +
                "StatusCode : "+httpStatusCode+"\n" +
                "Message : " + message +"\n" +
                "Time : " + nowStamp
                ;

    }

}
