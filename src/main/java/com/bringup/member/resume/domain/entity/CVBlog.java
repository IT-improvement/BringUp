package com.bringup.member.resume.domain.entity;

import com.bringup.member.resume.domain.entity.primaryKey.CVBlogPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cvBlog")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CVBlogPK.class)
public class CVBlog {

    @Id
    private int blogIndex;
    @Id
    private int cvIndex;
}
