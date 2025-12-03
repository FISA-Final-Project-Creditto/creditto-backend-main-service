package org.creditto.creditto_service.global.infra.creditrating;

import org.creditto.creditto_service.domain.credit_score.dto.CreditScorePredictReq;
import org.creditto.creditto_service.domain.credit_score.dto.CreditScoreReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "credit-rating",
        url = "${CREDIT_RATING_SERVER_URL}",
        configuration = CreditRatingFeignConfig.class
)
public interface CreditRatingFeignClient {

    @PostMapping(value = "/api/server/credit-score", consumes = MediaType.APPLICATION_JSON_VALUE)
    CreditScoreRes calculateCreditScore(@RequestBody CreditScoreReq request);

    @GetMapping(value = "/api/server/credit-score/{userId}")
    CreditScoreRes getCreditScore(@PathVariable("userId") Long userId);

    @GetMapping(value = "/api/server/credit-score/history/{userId}")
    CreditScoreHistoryRes getCreditScoreHistory(@PathVariable("userId") Long userId);

    @PostMapping(value = "/api/server/credit-score/prediction", consumes = MediaType.APPLICATION_JSON_VALUE)
    CreditScorePredictRes predictCreditScore(@RequestBody CreditScorePredictReq request);

    @GetMapping(value = "/api/server/credit-score/report/{userId}")
    CreditScoreReportRes  getCreditScoreReport(@PathVariable("userId") Long userId);
}
