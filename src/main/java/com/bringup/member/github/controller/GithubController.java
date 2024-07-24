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

    @GetMapping("/github/orgs/{org}/repos")
    public String getOrgRepos(@PathVariable String org, @RequestParam("token") String githubToken) {
        System.out.println("token: " + githubToken);
        return gitHubService.getOrgRepos(org, githubToken);
    }
}
