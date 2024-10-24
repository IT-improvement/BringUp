package com.bringup.member.portfolio.letter.domain;

import com.bringup.common.enums.StatusType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "letter")
public class LetterEntity {

    @Id
    private int userIndex;

    private String answser1;
    private String answser2;
    private String answser3;
    private String status;

    public LetterEntity(int userIndex){
        this.userIndex = userIndex;
        this.status = "생성";
    }
}
