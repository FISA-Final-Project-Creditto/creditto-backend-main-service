package org.creditto.creditto_service.domain.overseasRemittance.dto;

import org.creditto.creditto_service.global.common.CurrencyCode;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OverseasRemittanceReq(
        String accountNo,
        RecipientInfoReq recipientInfo,
        Long recurId,
        LocalDate startDate,
        CurrencyCode sendCurrency,
        CurrencyCode receiveCurrency,
        BigDecimal targetAmount
) {
}
