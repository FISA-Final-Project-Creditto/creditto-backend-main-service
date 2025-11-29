package org.creditto.creditto_service.domain.creditScore.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.creditScore.dto.CreditScorePredictReq;
import org.creditto.creditto_service.domain.creditScore.dto.CreditScoreReq;
import org.creditto.creditto_service.global.infra.creditrating.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditScoreService {

    private final CreditRatingFeignClient creditRatingFeignClient;

    public CreditScoreRes calculateCreditScore(CreditScoreReq creditScoreReq) {
        return creditRatingFeignClient.calculateCreditScore(creditScoreReq);
    }

    public CreditScoreRes getCreditScore(Long userId) {
        return creditRatingFeignClient.getCreditScore(userId);
    }

    public CreditScoreHistoryRes getCreditScoreHistory(Long userId) {
        return creditRatingFeignClient.getCreditScoreHistory(userId);
    }

    public CreditScorePredictRes predictCreditScore(CreditScorePredictReq creditScorePredictReq) {
        return creditRatingFeignClient.predictCreditScore(creditScorePredictReq);
    }
}
