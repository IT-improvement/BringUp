package com.bringup.member.proposeCV.controller;

import com.bringup.member.proposeCV.domain.ProposeCVEntity;
import com.bringup.member.proposeCV.domain.service.ProposeCVService;
import com.bringup.member.proposeCV.dto.ProposeCVRequest;
import com.bringup.member.proposeCV.dto.ProposeCVResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProposeCVController {
    private final ProposeCVService proposeCVService;
}
