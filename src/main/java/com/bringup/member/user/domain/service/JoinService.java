package com.bringup.member.user.domain.service;

import com.bringup.common.enums.RolesType;
import com.bringup.common.enums.StatusType;
import com.bringup.member.portfolio.letter.domain.LetterEntity;
import com.bringup.member.portfolio.letter.domain.LetterRepository;
import com.bringup.member.user.domain.entity.MilitaryEntity;
import com.bringup.member.user.domain.entity.UserEntity;
import com.bringup.member.user.domain.repository.MilitaryRepsitory;
import com.bringup.member.user.domain.repository.UserRepository;
import com.bringup.member.user.dto.JoinDTO;
import com.bringup.member.user.dto.MilitaryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class JoinService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LetterRepository letterRepository;
    private final MilitaryRepsitory militaryRepsitory;

    @Autowired
    public JoinService(UserRepository userRepository, PasswordEncoder passwordEncoder, LetterRepository letterRepository, MilitaryRepsitory militaryRepsitory) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.letterRepository = letterRepository;
        this.militaryRepsitory = militaryRepsitory;
    }

    public void joinProcess(JoinDTO joinDTO) {
        String email = joinDTO.getUserEmail();
        String password = joinDTO.getUserPassword();

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        Boolean isExist = userRepository.existsByUserEmail(email);

        if (isExist) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail(joinDTO.getUserEmail());
        userEntity.setUserPassword(passwordEncoder.encode(password));
        userEntity.setUserName(joinDTO.getUserName());
        userEntity.setUserAddress(joinDTO.getUserAddress());
        userEntity.setUserPhonenumber(joinDTO.getUserPhonenumber());
        userEntity.setUserBirthday(joinDTO.getUserBirthday());
        userEntity.setFreelancer(joinDTO.isFreelancer());
        userEntity.setGender(joinDTO.getGender());
        userEntity.setStatus(String.valueOf(StatusType.ACTIVE));
        userEntity.setRole(RolesType.ROLE_MEMBER);

        userRepository.save(userEntity);

        // Console 출력
        System.out.println("User 저장 완료: " + userEntity);

        Optional<UserEntity> user = userRepository.findByUserEmail(email);
        int userIndex = user.get().getUserIndex();

        // Letter 저장
        LetterEntity letterEntity = new LetterEntity(userIndex);
        letterRepository.save(letterEntity);

        // Military 데이터 저장
        if (joinDTO.getMilitaryList() != null) {
            for (MilitaryItem item : joinDTO.getMilitaryList()) {
                MilitaryEntity militaryEntity = new MilitaryEntity(item, userIndex);
                militaryRepsitory.save(militaryEntity);

                // Console 출력
                System.out.println("Military 저장 완료: " + militaryEntity);
            }
        } else {
            System.out.println("Military 데이터 없음");
        }
    }


    /**
     * ID(userEmail) Check
     */
    public boolean checkId(String userEmail){
        return !userRepository.findByUserEmail(userEmail).isPresent();
    }
}