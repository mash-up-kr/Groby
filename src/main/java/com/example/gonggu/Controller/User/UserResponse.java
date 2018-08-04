package com.example.gonggu.Controller.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class UserResponse {
    public HttpStatus status;
    public String message;
    public Object acceptJson;
}
