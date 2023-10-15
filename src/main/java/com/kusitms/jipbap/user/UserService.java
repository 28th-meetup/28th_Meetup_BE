package com.kusitms.jipbap.user;

import com.kusitms.jipbap.auth.exception.InvalidEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * 로그아웃 - User의 RefreshToken 제거
     * @param email
     */
    @Transactional
    public void logout(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new InvalidEmailException("회원정보가 존재하지 않습니다."));
        user.updateRefreshToken(null);
    }
}

