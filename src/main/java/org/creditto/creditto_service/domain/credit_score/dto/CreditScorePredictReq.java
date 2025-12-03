package org.creditto.creditto_service.domain.credit_score.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CreditScorePredictReq(
        @JsonProperty("user_id")
        Long userId,
        @JsonProperty("monthly_amount")
        BigDecimal monthlyAmount
) {
}
