package org.creditto.creditto_service.domain.loan.entity;

import lombok.Getter;

@Getter
public enum RepaymentType {
    EQUAL_INSTALLMENT("원리금균등상환"),
    EQUAL_PRINCIPAL("원금균등상환"),
    BULLET_REPAYMENT("원금만기상환");

    private final String type;

    RepaymentType(String type) { this.type = type; }
}
