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
}
