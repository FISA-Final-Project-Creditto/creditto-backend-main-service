package org.creditto.creditto_service.domain.transaction.enums;

import lombok.Getter;

@Getter
public enum TxnType {

    DEPOSIT("입금"),
    WITHDRAWAL("출금"),
    EXCHANGE("환전"),
    FEE("수수료");

    private final String type;

    TxnType(String type) {
        this.type = type;
    }
}
