package org.creditto.creditto_service.global.infra.corebanking;

import org.creditto.creditto_service.domain.account.dto.CreateAccountReq;
import org.creditto.creditto_service.domain.overseasRemittance.dto.OverseasRemittanceReq;
import org.creditto.creditto_service.global.common.CoreBankingRes;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceDto;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceResponseDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceRecordDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(
        name = "core-banking",
        url = "${CORE_BANKING_SERVER_URL}",
        configuration = CoreBankingFeignConfig.class
)
public interface CoreBankingFeignClient {
    /*
    ACCOUNT
     */
    @PostMapping(value = "/api/core/account/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    CoreBankingRes<AccountRes> createAccount(@PathVariable Long userId, @RequestBody CreateAccountReq request);

    @GetMapping(value = "/api/core/account/{accountId}")
    CoreBankingRes<AccountRes> getAccountByAccountId(@PathVariable Long accountId);

    @GetMapping(value = "/api/core/account/{accountId}/balance")
    CoreBankingRes<BigDecimal> getAccountBalanceByAccountId(@PathVariable Long accountId);

    @GetMapping(value = "/api/core/account")
    CoreBankingRes<AccountRes> getAccountByAccountNo(@RequestParam(name = "accountNo") String accountNo);

    @GetMapping(value = "/api/core/account")
    CoreBankingRes<List<AccountRes>> getAccountsByUserId(@RequestParam(name = "userId") Long userId);

    /*
    TRANSACTION
     */
    @GetMapping(value = "/api/core/transactions/{accountId}")
    CoreBankingRes<List<TransactionRes>> getTransactionByAccountId(@PathVariable Long accountId);

    /*
    REMITTANCE
     */
    @PostMapping(value = "/api/core/remittance/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    CoreBankingRes<OverseasRemittanceRes> processRemittanceOnce(@PathVariable Long userId, @RequestBody OverseasRemittanceReq request);

    // 등록된 정기 해외 송금 설정 조회
    @GetMapping("/api/core/remittance/schedule")
    List<RegularRemittanceDto> getScheduledRemittancesByUserId(@RequestParam("userId") String userId);

    // 한 건의 정기 해외 송금 기록 상세 조회
    @GetMapping("/api/core/remittance/schedule/{recurId}")
    List<RemittanceRecordDto> getRemittanceRecordsByRecurId(@PathVariable("recurId") String recurId, @RequestParam("userId") String userId);

    // 정기 해외 송금 내역 수정
    @PutMapping("/api/core/remittance/schedule/{recurId}")
    void updateScheduledRemittance(
            @PathVariable("recurId") String recurId,
            @RequestParam("userId") String userId,
            @RequestBody RegularRemittanceDto regularRemittanceDto
    );

    // 정기 해외 송금 설정 삭제
    @DeleteMapping("/api/core/remittance/schedule/{recurId}")
    void cancelScheduledRemittance(
            @PathVariable("recurId") String recurId,
            @RequestParam("userId") String userId
    );
}
