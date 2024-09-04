package com.bringup.common.chat.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatRestController {

    @GetMapping("/user")
    public void user(@AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("userCode: "+userDetails.getId());
    }
}
