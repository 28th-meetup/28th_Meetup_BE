package com.kusitms.jipbap.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.jipbap.auth.dto.*;
import com.kusitms.jipbap.auth.exception.*;
import com.kusitms.jipbap.security.jwt.JwtTokenProvider;
import com.kusitms.jipbap.security.jwt.TokenInfo;
import com.kusitms.jipbap.user.CountryPhoneCode;
import com.kusitms.jipbap.user.Role;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.UserRepository;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Member;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Value("${secret.pwd}")
    private String KAKAO_SECRET_SERVER_PWD;
    private final String INAPP = "IN_APP";
    private final String KAKAO = "KAKAO";

    /**
     * 회원 가입
     * @param dto
     */
    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto dto) {

        if(userRepository.existsByEmail(dto.getEmail())) throw new EmailExistsException("이미 가입한 이메일입니다.");
        if(userRepository.existsByUsername(dto.getUsername())) throw new UsernameExistsException("이미 존재하는 닉네임입니다.");
        User user = userRepository.save(
                User.builder()
                        .id(null)
                        .email(dto.getEmail())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .username(dto.getUsername())
                        .countryPhoneCode(dto.getCountryPhoneCode())
                        .phoneNum(dto.getPhoneNum())
                        .role(dto.getRole())
                        .refreshToken(null)
                        .oauth(INAPP)
                        .build()
        );
        return new SignUpResponseDto(user.getId(), user.getEmail(), user.getUsername());
    }

    /**
     * 로그인
     * @param email
     * @param password
     * @return
     */
    @Transactional
    public SignInResponseDto signIn(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new InvalidEmailException("회원정보가 존재하지 않습니다."));
        if(!passwordEncoder.matches(password, user.getPassword())) {
            if(user.getOauth().equals("KAKAO")) {
                throw new InvalidPasswordException("카카오 계정입니다. 카카오 로그인으로 시도해보세요.");
            }
            throw new InvalidPasswordException("잘못된 비밀번호입니다.");
        }

        TokenInfo accessToken = tokenProvider.createAccessToken(user.getEmail(), user.getRole());
        TokenInfo refreshToken = tokenProvider.createRefreshToken(user.getEmail(), user.getRole());
        user.updateRefreshToken(refreshToken.getToken());
        return new SignInResponseDto(
                user.getId(), user.getEmail(), user.getImage(), user.getRole(), accessToken.getToken(), refreshToken.getToken(), accessToken.getExpireTime(), refreshToken.getExpireTime()
        );

    }

    /**
     * 카카오 api 사용하여 유저 정보 받기
     * @param token
     * @return
     */
    @Transactional
    public KakaoProfileDto getKakaoProfile(String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        KakaoProfileDto profile;

        try {
            profile = objectMapper.readValue(response.getBody(), KakaoProfileDto.class);
        } catch(JsonMappingException e) {
            throw new JsonException("Json Mapping 오류가 발생했습니다.");
        } catch(JsonProcessingException e) {
            throw new JsonException("Json Processing 오류가 발생했습니다.");
        }

        return profile;
    }

    /**
     * 카카오 회원가입, 로그인
     * @param profile
     * @return
     */
    @Transactional
    public KakaoSignInResponseDto kakaoAutoSignIn(KakaoProfileDto profile) {
        boolean isSignUp = false;
        User kakaoUser = User.builder()
                .email(profile.getKakao_account().getEmail())
                .username(profile.getProperties().getNickname())
                .image(profile.getKakao_account().getProfile().getProfile_image_url())
                .password(KAKAO_SECRET_SERVER_PWD)
                .oauth(KAKAO)
                .build();

        if(userRepository.findByEmail(kakaoUser.getEmail()).isEmpty()) {
            log.info(profile.getKakao_account().getEmail()+": 기존 회원이 아니므로 자동 회원가입 후 로그인을 진행합니다.");
            isSignUp = true;
            signUp(new SignUpRequestDto(
                    kakaoUser.getEmail(),
                    kakaoUser.getPassword(),
                    kakaoUser.getUsername(),
                    CountryPhoneCode.KOREA, // 카카오
                    kakaoUser.getPhoneNum(),
                    Role.USER,
                    kakaoUser.getImage()
            ));
        } else {
            log.info(profile.getKakao_account().getEmail()+": 기존 회원이므로 자동 로그인을 진행합니다.");
        }

        User findUser = userRepository.findByEmail(kakaoUser.getEmail()).orElseThrow(()->new UserNotFoundException("카카오 회원가입 도중 문제가 발생했습니다."));
        findUser.updateOAuth(KAKAO);
        return new KakaoSignInResponseDto(signIn(kakaoUser.getEmail(), kakaoUser.getPassword()), isSignUp);
    }

    /**
     * refreshtoken 갱
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
        tokenProvider.validateToken(refreshToken);

        TokenInfo newAccessToken = tokenProvider.createAccessToken(user.getEmail(), user.getRole());
        TokenInfo newRefreshToken = tokenProvider.createRefreshToken(user.getEmail(), user.getRole());
        return new ReissueResponseDto(
                newAccessToken.getToken(), newRefreshToken.getToken()
        );
    }
}
