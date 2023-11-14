package com.kusitms.jipbap.user;

import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.user.entity.area.LocalArea;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id; //고유 pk

    @NotBlank
    @Column(unique = true)
    private String email; //로그인 email

    @NotBlank
    private String password; //비밀번호

    @NotBlank
    @Column(unique = true)
    private String username; //닉네임

    @ManyToOne
    @JoinColumn(name = "local_area_id")
    private LocalArea localArea; //지역

    @NotBlank
    private String address; //상세주소

    @NotBlank
    private String postalCode; //우편번호

    private String image; //프로필 사진

    private String phoneNum;

    @Enumerated(EnumType.STRING)
    private Role role; //USER, ADMIN

    private String refreshToken;
    private String oauth; //INAPP, KAKAO
    private String fcmToken;

    public void updateRefreshToken(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }
    public void updateOAuth(String oauth) {
        this.oauth = oauth;
    }
}