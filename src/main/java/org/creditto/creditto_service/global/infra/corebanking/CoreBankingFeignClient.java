package org.creditto.creditto_service.global.infra.corebanking;

import org.creditto.creditto_service.domain.account.dto.CreateAccountReq;
import org.creditto.creditto_service.domain.overseasRemittance.dto.OverseasRemittanceReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(
        name = "core-banking",
        url = "${CORE_BANKING_SERVER_URL}"
)
public interface CoreBankingFeignClient {
    @PostMapping(value = "/api/core/account/{externalUserId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    AccountRes createAccount(@PathVariable String externalUserId, @RequestBody CreateAccountReq request);

    @GetMapping(value = "/api/core/account/{accountId}/account")
    AccountRes getAccountByAccountId(@PathVariable Long accountId);

    @GetMapping(value = "/api/core/account/{accountId}/balance")
    BigDecimal getAccountBalanceByAccountId(@PathVariable Long accountId);

    @GetMapping(value = "/api/core/account/{accountNo}")
    AccountRes getAccountByAccountNo(@PathVariable String accountNo);

    @GetMapping(value = "/api/core/account/client/{externalUserId}")
    AccountRes getAccountByExternalUserId(@PathVariable String externalUserId);

    @GetMapping(value = "/api/core/transactions/{accountId}")
    List<TransactionRes> getTransactionByAccountId(@PathVariable Long accountId);

    @PostMapping(value = "/api/core/remittance/{externalUserId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    OverseasRemittanceRes processRemittanceOnce(@PathVariable String externalUserId, @RequestBody OverseasRemittanceReq request);

}