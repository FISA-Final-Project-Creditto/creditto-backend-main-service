package org.creditto.creditto_service.domain.creditScore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreditScoreReq(
        @JsonProperty("user_id")
        Long userId
) {
}
