package org.creditto.creditto_service.global.infra.creditrating;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreditScoreRes(
        @JsonProperty("credit_score")
        Integer creditScore
) {
}
