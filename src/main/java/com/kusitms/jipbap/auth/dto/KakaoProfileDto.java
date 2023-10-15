package com.kusitms.jipbap.auth.dto;

import lombok.Data;

@Data
public class KakaoProfileDto {

    private Long id;
    private Properties properties;
    private KakaoAccount kakao_account;
    private String connected_at;

    @Data
    public class Properties {
        private String nickname;
    }

    @Data
    public class KakaoAccount {
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;
        private Profile profile;

        @Data
        public class Profile {
            private String profile_image_url;
        }
    }
}
