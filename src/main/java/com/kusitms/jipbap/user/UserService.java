package com.kusitms.jipbap.user;

import com.kusitms.jipbap.auth.exception.InvalidEmailException;
import com.kusitms.jipbap.auth.exception.UsernameExistsException;
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

    public String checkNicknameIsDuplicate(String nickname){
        if(userRepository.existsByUsername(nickname)) throw new UsernameExistsException("이미 존재하는 닉네임입니다.");
        return "사용 가능한 닉네임입니다.";
    }
}

