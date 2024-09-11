package com.bringup.member.page;


import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class memberPageController {
    @GetMapping("/main")
    public String mainPage(){
        return "common/main/main";
    }

    @GetMapping()
    public String companyPage(){
        return "member/company";
    }
}
