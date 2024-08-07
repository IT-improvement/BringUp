package com.bringup.member.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/member/login")
    public String loginP(){
        return "login";
    }
   /* @GetMapping("/loginProc")*/

}
