package org.creditto.creditto_service.global.infra.creditrating;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CreditScorePredictRes(
        @JsonProperty("user_id")
        Integer userId,
        @JsonProperty("monthly_remit_amount")
        BigDecimal monthlyRemitAmount,
        @JsonProperty("current_score")
        Integer currentScore,
        @JsonProperty("after_6m")
        CreditScorePredict after6m,
        @JsonProperty("after_12m")
        CreditScorePredict after12m,
        @JsonProperty("after_18m")
        CreditScorePredict after18m
) {
}
