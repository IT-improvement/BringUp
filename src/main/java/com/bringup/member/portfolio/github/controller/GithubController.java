package com.bringup.member.portfolio.github.controller;

import com.bringup.member.portfolio.github.domain.service.GithubService;
import com.bringup.member.portfolio.github.dto.GithubRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GithubController {

    private final GithubService gitHubService;

    @GetMapping("/github/user")
    public String getGitHubUserData(@RequestParam("token") String githubToken) {
        return gitHubService.getUserData(githubToken);
    }

    @GetMapping("/github/user/repos")
    public String getUserRepos(@RequestBody GithubRequestDto githubToken) {
        String userLogin = gitHubService.getUserLogin(githubToken.getToken());
        return gitHubService.getOrgRepos(userLogin, githubToken.getToken());
    }
}
