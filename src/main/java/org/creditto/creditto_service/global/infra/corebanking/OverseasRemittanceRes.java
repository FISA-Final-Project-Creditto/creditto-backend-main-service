package org.creditto.creditto_service.global.infra.corebanking;

import org.creditto.creditto_service.domain.overseasRemittance.enums.RemittanceStatus;
import org.creditto.creditto_service.global.common.CurrencyCode;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OverseasRemittanceRes(
        Long remittanceId,
        Long userId,
        Long recipientId,
        String recipientName,
        Long accountId,
        String accountNo,
        Long recurId,
        Long exchangeId,
        BigDecimal exchangeRate,
        Long feeRecordId,
        CurrencyCode sendCurrency,
        CurrencyCode receiveCurrency,
        BigDecimal sendAmount,
        BigDecimal receiveAmount,
        LocalDate startDate,
        RemittanceStatus remittanceStatus
) {
}
