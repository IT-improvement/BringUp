package com.bringup.common.bookmark.domain.entity;

import com.bringup.common.enums.BookmarkType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "company_bookmark")
public class CompanyBookMarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_index")
    private int companyIndex;

    @Column(name = "user_index")
    private int userIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookmarkType status = BookmarkType.BOOKMARK;
}
