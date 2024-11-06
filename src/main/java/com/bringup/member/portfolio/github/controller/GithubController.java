package com.bringup.member.portfolio.github.controller;

import com.bringup.common.security.service.UserDetailsImpl;
import com.bringup.member.portfolio.github.domain.service.GithubService;
import com.bringup.member.portfolio.github.dto.GithubRequestDto;
import com.bringup.member.portfolio.github.dto.GithubResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GithubController {

    private final GithubService gitHubService;

    @GetMapping("/github/user")
    public String getGitHubUserData(@AuthenticationPrincipal UserDetailsImpl user) {
        int userCode = user.getId();
        return gitHubService.getUserData(userCode);
    }

    @GetMapping("/github/user/repos")
    public String getUserRepos(@AuthenticationPrincipal UserDetailsImpl user) {
        int userCode = user.getId();
        String userLogin = gitHubService.getUserLogin(userCode);
        return gitHubService.getOrgRepos(userLogin, userCode);
    }

    @PutMapping("/github/insert")
    public ResponseEntity<? super GithubResponseDto> insertToken(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody String token){
        int userCode = user.getId();
        return gitHubService.insertGithubToken(userCode ,token);
    }
}
