package com.bringup.member.user.domain.entity;

import com.bringup.member.user.dto.MilitaryItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "military")
public class MilitaryEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userIndex;
    private String militaryStatus;
    private String militaryType;
    private String specialty;
    private String rankName;
    private String dischargeReason;
    private Date enlistmentDate;
    private Date dischargeDate;
    private String exemptionReason;

    public MilitaryEntity(MilitaryItem item, int userIndex) {
        this.userIndex = userIndex;
        this.militaryStatus = item.getMilitaryStatus();
        this.militaryType = item.getMilitaryType();
        this.specialty = item.getSpecialty();
        this.rankName = item.getRankName();
        this.dischargeReason = item.getDischargeReason();
        this.enlistmentDate = item.getEnlistmentDate();
        this.dischargeDate = item.getDischargeDate();
        this.exemptionReason = item.getExemptionReason();
    }
}
