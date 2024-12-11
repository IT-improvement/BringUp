package com.bringup.member.portfolio.github.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "github")
public class GithubEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int githubIndex;
    private String githubUrl;
    private int cvIndex;

    public GithubEntity(String githubUrl, int cvIndex) {
        this.githubUrl = githubUrl;
        this.cvIndex = cvIndex;
    }
}
