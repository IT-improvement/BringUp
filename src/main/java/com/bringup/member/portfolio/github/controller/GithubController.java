package com.bringup.member.portfolio.github.controller;

import com.bringup.member.portfolio.github.domain.service.GithubService;
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
    public String getUserRepos(@RequestParam("token") String githubToken) {
        String userLogin = gitHubService.getUserLogin(githubToken);
        return gitHubService.getOrgRepos(userLogin, githubToken);
    }
}
