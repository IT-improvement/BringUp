package com.bringup.member.proposeCV.controller;

import com.bringup.member.proposeCV.domain.ProposeCVEntity;
import com.bringup.member.proposeCV.domain.service.ProposeCVService;
import com.bringup.member.proposeCV.dto.ProposeCVRequest;
import com.bringup.member.proposeCV.dto.ProposeCVResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProposeCVController {
    private final ProposeCVService proposeCVService;

    @GetMapping("/proposeCV/")
    public String findAll(Model model){
        List<ProposeCVEntity> proposeCVList = proposeCVService.findAll();
        model.addAttribute("proposeCVList", proposeCVList);
        return "list";
    }
}
