package org.creditto.creditto_service.domain.consent.entity;

import lombok.Getter;

@Getter
public enum ConsentCategory {

    SIGNUP("회원 가입"),
    CREDIT("신용 분석"),
    REMITTANCE("해외 송금"),
    ETC("기타");

    private final String description;

    ConsentCategory(String description) {
        this.description = description;
    }
}
