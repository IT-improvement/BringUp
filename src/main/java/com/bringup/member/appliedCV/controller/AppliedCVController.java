package com.bringup.member.appliedCV.controller;

import com.bringup.member.appliedCV.domain.AppliedCVEntity;
import com.bringup.member.appliedCV.domain.AppliedCVService;
import com.bringup.member.appliedCV.dto.AppliedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AppliedCVController {
    private final AppliedCVService appliedCVService;

    @GetMapping("/appliedCV/")
    public String findAll(Model model){
        List<AppliedCVEntity> appliedList = appliedCVService.findAll();
        model.addAttribute("appliedList", appliedList);
        return "list";
    }
}
