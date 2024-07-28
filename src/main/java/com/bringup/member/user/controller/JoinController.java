package com.bringup.member.user.controller;

import com.bringup.member.user.domain.service.JoinService;
import com.bringup.member.user.dto.JoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {


    @Autowired
    private JoinService joinService; //원래는 생성자로 하는게 좋음

    @GetMapping("/join")
    public String join(){
        return "member/join";
    }

   @PostMapping("/joinProc")
   public String joinPocess(JoinDTO joinDTO){

        System.out.println(joinDTO.getUserEmail());

        joinService.joinProcess(joinDTO);

        return "redirect:/login";
   }
}
