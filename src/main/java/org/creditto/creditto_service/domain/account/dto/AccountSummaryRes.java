package org.creditto.creditto_service.domain.account.dto;

import java.math.BigDecimal;

public record AccountSummaryRes(
        long accountCount,
        BigDecimal totalBalance
) {
}
