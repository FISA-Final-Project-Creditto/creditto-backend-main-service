package org.creditto.creditto_service.global.infra.corebanking;

import org.creditto.creditto_service.domain.account.enums.AccountType;

import java.math.BigDecimal;

public record AccountRes(
        String accountNo,
        String accountName,
        BigDecimal balance,
        AccountType accountType,
        String accountState,
        String clientId
) {
}
