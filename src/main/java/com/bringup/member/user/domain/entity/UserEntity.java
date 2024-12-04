package com.bringup.member.user.domain.entity;

import com.bringup.common.enums.RolesType;
import com.bringup.common.enums.StatusType;
import com.bringup.member.user.dto.JoinDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_index") // 데이터베이스의 user_index 컬럼과 매핑
    private Integer userIndex;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "user_phonenumber")
    private String userPhonenumber;

    @Column(name = "user_birthday")
    private String userBirthday;

    @Column(name = "freelancer")
    private boolean freelancer;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RolesType role = RolesType.ROLE_MEMBER;

    @Column(name = "github_token")
    private String githubToken;

    public UserEntity(JoinDTO joinDTO){
        this.userEmail = joinDTO.getUserEmail();
        this.userName = joinDTO.getUserName();
        this.userPhonenumber=joinDTO.getUserPhonenumber();
        this.userBirthday=joinDTO.getUserBirthday();
    }
}
