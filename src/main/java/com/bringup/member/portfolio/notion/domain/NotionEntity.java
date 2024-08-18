package com.bringup.member.portfolio.notion.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity(name = "notion")
public class NotionEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notionIndex;
}
