package org.creditto.creditto_service.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.account.dto.CreateAccountReq;
import org.creditto.creditto_service.global.infra.corebanking.AccountRes;
import org.creditto.creditto_service.global.infra.corebanking.CoreBankingFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final CoreBankingFeignClient coreBankingFeignClient;

    @Transactional
    public AccountRes createAccount(String userId, CreateAccountReq request) {
        return coreBankingFeignClient.createAccount(userId, request);
    }

    public AccountRes getAccountByAccountId(Long accountId) {
        return coreBankingFeignClient.getAccountByAccountId(accountId);
    }

    public BigDecimal getAccountBalanceByAccountId(Long accountId) {
        return coreBankingFeignClient.getAccountBalanceByAccountId(accountId);
    }

    public AccountRes getAccountByAccountNo(String accountNo) {
        return coreBankingFeignClient.getAccountByAccountNo(accountNo);
    }

    public AccountRes getAccountByExternalUserId(String externalUserId) {
        return coreBankingFeignClient.getAccountByExternalUserId(externalUserId);
    }

}