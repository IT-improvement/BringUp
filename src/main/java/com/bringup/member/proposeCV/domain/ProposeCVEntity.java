package com.bringup.member.proposeCV.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "proposeCV")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProposeCVEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int proposeCVIndex;
    private int cvIndex;
    private String proposeTime;
    @Builder
    public ProposeCVEntity(String proposeTime){
        this.proposeTime = proposeTime;
    }
}
