package com.bringup.member.resume.domain.repository;

import com.bringup.member.resume.domain.entity.CVBlog;
import com.bringup.member.resume.domain.entity.primaryKey.CVBlogPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CVBlogRepository extends JpaRepository<CVBlog, CVBlogPK> {
    List<CVBlog> findByCvIndex(int cvIndex);
}
