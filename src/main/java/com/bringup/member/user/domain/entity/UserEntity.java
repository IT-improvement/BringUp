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
    private int userIndex;

    private String userEmail;

    private String userPassword;

    private String userName;

    private String userAddress;

    private String userPhonenumber;

    private String userBirthday;

    private boolean freelancer;

    private String status;
    @Enumerated(EnumType.STRING) //@Enumerated(EnumType.STRING)
    private RolesType role = RolesType.ROLE_MEMBER;
}
