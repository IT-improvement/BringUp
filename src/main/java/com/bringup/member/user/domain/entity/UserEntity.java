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
    private int userIndex;

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

    public void updateUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public void updatePassword(String userPassword){
        this.userPassword = userPassword;
    }

    public void updateUserName(String userName){
        this.userName = userName;
    }

    public void updateUserPhoneNumber(String userPhonenumber){
        this.userPhonenumber = userPhonenumber;
    }

    public void updateUserAddress(String userAddress){
        this.userAddress = userAddress;
    }

    public void updateUserBirthday(String userBirthday){
        this.userBirthday = userBirthday;
    }

    public void updateFreeLancer(boolean freelancer){
        this.freelancer = freelancer;
    }

    public void updateStatus(String status){
        this.status = status;
    }
}
