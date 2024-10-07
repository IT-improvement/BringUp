package com.bringup.member.user.domain.entity;

import com.bringup.common.enums.RolesType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_index") // 데이터베이스의 user_index 컬럼과 매핑
    private Integer userIndex;

    private String userEmail;

    private String userPassword;

    private String userName;

    private String userAddress;

    private String userPhonenumber;

    private String userBirthday;

    private boolean freelancer;

    private String status;

    @Enumerated(EnumType.STRING)
    private RolesType role = RolesType.ROLE_MEMBER;



}
