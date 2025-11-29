package org.creditto.creditto_service.global.infra.creditrating;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreditScoreHistory(
        Integer year,
        Integer month,
        @JsonProperty("avg_score")
        Integer avgScore
) {
}
