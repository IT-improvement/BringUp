package com.bringup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/main")
    public String main() {
        return "main"; // 실제 파일명은 main.jsp
    }
}
