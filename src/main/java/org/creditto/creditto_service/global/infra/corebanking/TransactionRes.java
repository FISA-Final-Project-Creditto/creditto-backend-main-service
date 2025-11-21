package org.creditto.creditto_service.global.infra.corebanking;

import org.creditto.creditto_service.domain.transaction.enums.TxnType;

import java.math.BigDecimal;

public record TransactionRes(
        Long accountId,
        BigDecimal txnAmount,
        TxnType txnType,
        Long typeId
) {
}
