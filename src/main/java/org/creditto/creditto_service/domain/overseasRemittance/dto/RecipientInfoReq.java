package org.creditto.creditto_service.domain.overseasRemittance.dto;

import org.creditto.creditto_service.global.common.CurrencyCode;

public record RecipientInfoReq(
        String name,
        String accountNo,
        String bankName,
        String bankCode,
        String phoneCc,
        String phoneNo,
        String country,
        CurrencyCode receiveCurrency
) { }
