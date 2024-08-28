package com.bringup.member.membership.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_membership")
public class UserMembership {

    @Id
    @Column(name = "user_index", nullable = false)
    private int userIndex;

    @Column(name = "start_date", nullable = false, length = 30)
    private String startDate;

    @Column(name = "period", nullable = false, length = 30)
    private String period;
}
