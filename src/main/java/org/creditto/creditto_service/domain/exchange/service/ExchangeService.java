package org.creditto.creditto_service.domain.exchange.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.global.infra.corebanking.CoreBankingFeignClient;
import org.creditto.creditto_service.global.infra.corebanking.ExchangeRes;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final CoreBankingFeignClient coreBankingFeignClient;

    public ExchangeRes getExchangeRateByCurrency(String currencyCode) {
        return coreBankingFeignClient.getExchangeRateByCurrency(currencyCode).data();
    }
}
