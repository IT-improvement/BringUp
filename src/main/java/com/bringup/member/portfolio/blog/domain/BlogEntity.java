package com.bringup.member.portfolio.blog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blog")
@Entity
public class BlogEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int blogIndex;
    private int userIndex;
    private String url;
}
