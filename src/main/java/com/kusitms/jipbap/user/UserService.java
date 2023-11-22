package com.kusitms.jipbap.user;

import com.kusitms.jipbap.auth.dto.ReissueResponseDto;
import com.kusitms.jipbap.auth.exception.InvalidEmailException;
import com.kusitms.jipbap.auth.exception.RefreshTokenNotFoundException;
import com.kusitms.jipbap.auth.exception.UsernameExistsException;
import com.kusitms.jipbap.security.jwt.JwtTokenProvider;
import com.kusitms.jipbap.security.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    /**
     * refreshtoken 갱신
     * @param email
     * @param refreshToken
     * @return
     */
    @Transactional
    public ReissueResponseDto reissue(String email, String refreshToken) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new InvalidEmailException("회원정보가 존재하지 않습니다."));
        if(!user.getRefreshToken().equals(refreshToken)) {
            throw new RefreshTokenNotFoundException("리프레쉬 토큰에서 유저정보를 찾을 수 없습니다.");
        }
        tokenProvider.validateToken(tokenProvider.resolveToken(refreshToken));

        TokenInfo newAccessToken = tokenProvider.createAccessToken(user.getEmail(), user.getRole());
        TokenInfo newRefreshToken = tokenProvider.createRefreshToken(user.getEmail(), user.getRole());
        return new ReissueResponseDto(
                newAccessToken.getToken(), newRefreshToken.getToken()
        );
    }

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

