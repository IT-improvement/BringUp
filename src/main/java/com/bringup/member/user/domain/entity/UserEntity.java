package com.bringup.member.user.domain.entity;

import com.bringup.common.enums.RolesType;
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

    public UserEntity(JoinDTO joinDTO){
        this.userEmail = joinDTO.getUserEmail();
        this.userName = joinDTO.getUserName();
        this.userPhonenumber=joinDTO.getUserPhonenumber();
        this.userBirthday=joinDTO.getUserBirthday();
    }
}
