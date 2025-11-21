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
        return coreBankingFeignClient.createAccount(userId, request).data();
    }

    public AccountRes getAccountByAccountId(Long accountId) {
        return coreBankingFeignClient.getAccountByAccountId(accountId).data();
    }

    public BigDecimal getAccountBalanceByAccountId(Long accountId) {
        return coreBankingFeignClient.getAccountBalanceByAccountId(accountId).data();
    }

    public AccountRes getAccountByAccountNo(String accountNo) {
        return coreBankingFeignClient.getAccountByAccountNo(accountNo).data();
    }

    public AccountRes getAccountByExternalUserId(String externalUserId) {
        return coreBankingFeignClient.getAccountByExternalUserId(externalUserId).data();
    }

}