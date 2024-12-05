package com.bringup.member.portfolio.github.domain.service;

import com.bringup.common.response.ResponseDto;
import com.bringup.member.portfolio.github.dto.GithubResponseDto;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.util.Optional;

@Service
public class GithubService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    public GithubService(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    public String getUserData(int userCode) {

        Optional<UserEntity> user = userRepository.findByUserIndex(userCode);
        UserEntity userEntity = user.get();
        String githubToken = userEntity.getGithubToken();
        System.out.println("toke:" + githubToken);
        String url = "https://api.github.com/user";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);
        headers.set("Accept", "application/vnd.github.v3+json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
    public String getUserLogin(int userCode) {
        String userData = getUserData(userCode);
        JSONObject jsonObject = new JSONObject(userData);
        return jsonObject.getString("login");
    }

    public String getOrgRepos(String org,int userCode) {
        Optional<UserEntity> user = userRepository.findByUserIndex(userCode);
        UserEntity userEntity = user.get();
        String githubToken = userEntity.getGithubToken();
        String url = "https://api.github.com/users/" + org + "/repos";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);
        headers.set("Accept", "application/vnd.github+json");
        headers.set("X-GitHub-Api-Version"," 2022-11-28");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public ResponseEntity<? super GithubResponseDto> insertGithubToken(int userCode,String token){
        Optional<UserEntity> user = userRepository.findByUserIndex(userCode);
        if(!user.isPresent()){
            return ResponseDto.databaseError();
        }
        UserEntity userEntity = user.get();
        userEntity.setGithubToken(token);
        userRepository.save(userEntity);
        return GithubResponseDto.success();
    }

}
