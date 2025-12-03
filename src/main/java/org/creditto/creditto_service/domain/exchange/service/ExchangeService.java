package org.creditto.creditto_service.domain.exchange.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.global.infra.corebanking.CoreBankingFeignClient;
import org.creditto.creditto_service.global.infra.corebanking.ExchangeRes;
import org.creditto.creditto_service.global.infra.corebanking.PreferentialRateRes;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final CoreBankingFeignClient coreBankingFeignClient;

    public ExchangeRes getExchangeRateByCurrency(String currencyCode) {
        return Optional.ofNullable(coreBankingFeignClient.getExchangeRateByCurrency(currencyCode).data())
                .orElseThrow(() -> new NoSuchElementException("환율 정보를 찾을 수 없습니다: " + currencyCode));
    }

    public PreferentialRateRes getPreferentialRateByUserId(Long userId) {
        return coreBankingFeignClient.getPreferentialRate(userId).data();
    }
}
