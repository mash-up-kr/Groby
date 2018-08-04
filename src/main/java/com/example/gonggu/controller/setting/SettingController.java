package com.example.gonggu.controller.setting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setting/*")
public class SettingController {
    @GetMapping("/category")
    public String returnNowSetting(){
        return "now Setting is ...";
    }
}
