package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.comm.util.DateConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;

    // 회원 등록
    public User createUser(UserParams params) {
        User user = User.builder()
                .userNickname(params.getUserNickname())
                .userPhoneNum(params.getUserPhoneNum())
                .userBirth(DateConverter.strToLocalDate(params.getUserBirth()))
                .userGender(params.getUserGender())
                .marketingAgreeYn(params.getMarketingAgreeYn())
                .build();

        return userJpaRepository.save(user);
    }

    // 회원 전체 조회
    public Page<User> getAllUsers(Pageable pageable) {
        return userJpaRepository.findAll(pageable);
    }

    // 회원정보 수정
    public User modifyUser(Long userId, UserParams params) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.update(params);

        return user;
    }

    // 회원 삭제
    public void deleteUser(Long userId) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userJpaRepository.delete(user);
    }
}
