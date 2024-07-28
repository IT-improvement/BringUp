package com.bringup.member.github.controller;

import com.bringup.member.github.domain.service.GithubService;
import org.springframework.web.bind.annotation.*;

@RestController
public class GithubController {

    private final GithubService gitHubService;

    public GithubController(GithubService gitHubService) {
        this.gitHubService = gitHubService;
    }

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
