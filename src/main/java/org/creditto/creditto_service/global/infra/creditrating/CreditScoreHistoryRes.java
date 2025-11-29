package org.creditto.creditto_service.global.infra.creditrating;

import java.util.List;

public record CreditScoreHistoryRes(
        List<CreditScoreHistory> history
) {
}
