package com.bringup.member.user.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIndex;

    private String userEmail;

    private String userPassword;

    private String userName;

    private String userAddress;

    private String userPhoneNumber;

    private String userBirthday;

    private boolean freelancer;

    private String status;

    private String role;

}
