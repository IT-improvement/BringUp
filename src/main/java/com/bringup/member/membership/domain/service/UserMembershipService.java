package com.bringup.member.membership.domain.service;

import com.bringup.member.membership.domain.entity.UserMembership;
import com.bringup.member.membership.domain.repository.UserMembershipRepository;
import com.bringup.member.membership.dto.request.UserMembershipDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserMembershipService {
    private final UserMembershipRepository userMembershipRepository;

    public UserMembershipDto subscribeMembership(Long userIndex, String period){
        UserMembership membership = new UserMembership();
        membership.setUserIndex(userIndex);
        membership.setStartDate(LocalDate.now().toString()); // LocalDate를 String으로 변환하여 저장
        membership.setPeriod(period);

        System.out.println("Saving Membership: " + membership);
        userMembershipRepository.save(membership);

        UserMembershipDto dto = new UserMembershipDto();
        dto.setUserIndex(membership.getUserIndex());
        dto.setStartDate(membership.getStartDate());
        dto.setPeriod(membership.getPeriod());

        System.out.println("Returning DTO: " + dto);
        return dto;
    }
}