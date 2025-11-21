package org.creditto.creditto_service.domain.account.dto;

import org.creditto.creditto_service.domain.account.enums.AccountType;

public record CreateAccountReq(
        String accountName,
        AccountType accountType
) { }