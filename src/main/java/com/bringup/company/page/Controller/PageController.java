package com.bringup.company.page.Controller;


import com.bringup.common.response.BfResponse;
import com.bringup.company.member.DTO.request.ValidationRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static com.bringup.common.enums.GlobalSuccessCode.SUCCESS;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class PageController {

    @PostMapping("/")
    public String mainPage(){
        return "company_main_content.jsp";
    }
}
