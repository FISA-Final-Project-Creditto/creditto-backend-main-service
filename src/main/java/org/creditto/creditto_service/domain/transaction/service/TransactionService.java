package org.creditto.creditto_service.domain.transaction.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.global.common.CoreBankingRes;
import org.creditto.creditto_service.global.infra.corebanking.CoreBankingFeignClient;
import org.creditto.creditto_service.global.infra.corebanking.TransactionRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {

    private final CoreBankingFeignClient coreBankingFeignClient;

    public List<TransactionRes> getTransactionByAccountId(Long accountId) {
        return coreBankingFeignClient.getTransactionByAccountId(accountId).data();
    }
}

