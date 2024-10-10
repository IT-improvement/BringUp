package com.bringup.admin.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page/admin")
public class AdminPageController {

    @GetMapping("/payment/test")
    public String payTest(){
        return "admin/payment/Payment";
    }
}
