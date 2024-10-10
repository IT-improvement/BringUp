package com.bringup.common.chat.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/chat/test")
    public String test(){
        return "common/chat/index";
    }
}
