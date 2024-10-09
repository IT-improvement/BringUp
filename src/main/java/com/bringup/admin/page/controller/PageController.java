package com.bringup.admin.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/page")
public class PageController {

    @GetMapping("/payment/test")
    public String payTest(){
        return "admin/payment/Payment";
    }
}
