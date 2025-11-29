package org.creditto.creditto_service.domain.creditScore.controller;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.creditScore.dto.CreditScorePredictReq;
import org.creditto.creditto_service.domain.creditScore.dto.CreditScoreReq;
import org.creditto.creditto_service.domain.creditScore.service.CreditScoreService;
import org.creditto.creditto_service.global.infra.creditrating.CreditScoreHistoryRes;
import org.creditto.creditto_service.global.infra.creditrating.CreditScorePredictRes;
import org.creditto.creditto_service.global.infra.creditrating.CreditScoreRes;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit-score")
@RequiredArgsConstructor
public class CreditScoreController {

    private final CreditScoreService creditScoreService;

    @PostMapping()
    public CreditScoreRes calculateCreditScore(@RequestBody CreditScoreReq request) {
        return creditScoreService.calculateCreditScore(request);
    }

    @GetMapping("/{userId}")
    public CreditScoreRes getCreditScore(@PathVariable Long userId) {
        return creditScoreService.getCreditScore(userId);
    }

    @GetMapping("/history/{userId}")
    public CreditScoreHistoryRes getCreditScoreHistory(@PathVariable Long userId) {
        return creditScoreService.getCreditScoreHistory(userId);
    }

    @PostMapping("/prediction")
    public CreditScorePredictRes predictCreditScore(@RequestBody CreditScorePredictReq request) {
        return creditScoreService.predictCreditScore(request);
    }
}