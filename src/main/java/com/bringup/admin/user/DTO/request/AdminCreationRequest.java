package com.bringup.admin.user.DTO.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreationRequest {
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userPhonenumber;
}