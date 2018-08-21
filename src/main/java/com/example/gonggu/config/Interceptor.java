package com.example.gonggu.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Component
public class Interceptor extends HandlerInterceptorAdapter {
//    private static final Logger logger = Logger.getLogger(InterceptorConfig.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean check = true;

        if(request.getMethod().equals("POST")  || request.getMethod().equals("PATCH")){
            System.out.println(request.getMethod());
            if(request.getHeader("team") != "gg" ){
                System.out.println(false);
                check = false;
            }

        }
        return check;
    }

}
