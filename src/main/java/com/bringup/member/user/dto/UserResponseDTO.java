package com.bringup.member.user.dto;

import com.bringup.member.user.domain.entity.MilitaryEntity;
import com.bringup.member.user.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String userName;
    private String userEmail;
    private String userAddress;
    private String userPhonenumber;
    private String userBirthday;
    private String militaryStatus;

    public UserResponseDTO(UserEntity user, MilitaryEntity military) {
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.userAddress = user.getUserAddress();
        this.userPhonenumber = user.getUserPhonenumber();
        this.userBirthday = user.getUserBirthday();
        this.militaryStatus = (military != null) ? military.getMilitaryStatus() : "미필";
    }
}
