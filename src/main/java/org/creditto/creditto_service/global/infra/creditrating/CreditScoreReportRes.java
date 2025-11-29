package org.creditto.creditto_service.global.infra.creditrating;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreditScoreReportRes(
    @JsonProperty("credit_score")
    Integer creditScore,

    CreditScoreFeaturesRes features
) {}
