package com.bringup.member.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberUpdateDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "주소를 입력해주세요.")
    private String userAddress;

    @NotBlank(message = "전화번호를 입력해주세요")
    private String userPhoneNumber;

    private String userBirthday;
}
