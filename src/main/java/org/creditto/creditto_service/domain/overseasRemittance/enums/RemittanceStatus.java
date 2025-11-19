package org.creditto.creditto_service.domain.overseasRemittance.enums;

import lombok.Getter;

@Getter
public enum RemittanceStatus {

    REQUESTED("요청"),
    PENDING("대기"),
    PROCESSING("처리 중"),
    COMPLETED("완료"),
    FAILED("실패");

    private final String status;

    RemittanceStatus(String status) {
        this.status = status;
    }
}
