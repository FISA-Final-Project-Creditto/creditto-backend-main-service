package org.creditto.creditto_service.domain.credit_score.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreditScoreReq(
        @JsonProperty("user_id")
        Long userId
) {
}
