package org.creditto.creditto_service.domain.remittance.enums;

import lombok.Getter;

@Getter
public enum RegRemStatus {
    ACTIVE("정상"),
    PAUSED("일시중지"),
    CANCELLED("취소");

    private final String status;

    RegRemStatus(String status){
        this.status = status;
    }
}
