package com.example.gonggu.config;

import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class NowTime {
    private Date nowTime;

    @Override
    public String toString(){
        Date now = new Date();
        return now.toString();
    }

}
