package org.creditto.creditto_service.domain.admin.dto;

import java.math.BigDecimal;

public record RemittanceSummaryView(
        int totalCount,
        BigDecimal totalAmount,
        long activeCount,
        long pausedCount,
        long stoppedCount
) {
    public static RemittanceSummaryView empty() {
        return new RemittanceSummaryView(0, BigDecimal.ZERO, 0, 0, 0);
    }
}
