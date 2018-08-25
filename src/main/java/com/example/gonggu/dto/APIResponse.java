package com.example.gonggu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse<R> {
    public HttpStatus status;
    public String message;
    public Object acceptJson;
    public R returnJson;
}
