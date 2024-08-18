package com.bringup.common.event.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "email_verification_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String expiryDate;

    @Column(nullable = false, unique = true)
    private String email;
}