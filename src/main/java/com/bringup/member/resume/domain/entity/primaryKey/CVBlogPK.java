package com.bringup.member.resume.domain.entity.primaryKey;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CVBlogPK implements Serializable {

    @Column(name = "blog_index")
    private int blogIndex;

    @Column(name = "cvIndex")
    private int cvIndex;
}
