package com.example.gonggu.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class APIResponse {
    public HttpStatus status;
    public String message;
    public Object acceptJson;
    public Object returnJson;
}
