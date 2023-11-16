package com.kusitms.jipbap.user;

public enum CountryPhoneCode {
    KOREA("+82"),
    USA("+1"),
    CANADA("+1");

    private String code;

    CountryPhoneCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
