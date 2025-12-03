package org.creditto.creditto_service.global.infra.corebanking;

import org.creditto.creditto_service.domain.account.dto.AccountPasswordConfirmReq;
import org.creditto.creditto_service.domain.account.dto.AccountSummaryRes;
import org.creditto.creditto_service.domain.account.dto.CreateAccountReq;
import org.creditto.creditto_service.domain.overseas_remittance.dto.OverseasRemittanceReq;
import org.creditto.creditto_service.domain.remittance.dto.*;
import org.creditto.creditto_service.global.common.CoreBankingRes;
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

    @GetMapping(value = "/api/core/account", params = "accountNo")
    CoreBankingRes<AccountRes> getAccountByAccountNo(@RequestParam("accountNo") String accountNo);

    @GetMapping(value = "/api/core/account", params = "userId")
    CoreBankingRes<List<AccountRes>> getAccountsByUserId(@RequestParam("userId") Long userId);

    @GetMapping(value = "/api/core/account/balance/total")
    CoreBankingRes<AccountSummaryRes> getTotalBalanceByUserId(@RequestParam("userId") Long userId);

    @PostMapping(value = "/api/core/account/{accountId}/verify-password")
    CoreBankingRes<Void> verifyPassword(@PathVariable("accountId") Long accountId, @RequestBody AccountPasswordConfirmReq req);

    /*
    TRANSACTION
     */
    @GetMapping(value = "/api/core/transactions/{accountId}")
    CoreBankingRes<List<TransactionRes>> getTransactionByAccountId(@PathVariable Long accountId);

    /*
    REMITTANCE
     */
    @PostMapping(value = "/api/core/remittance/once/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    CoreBankingRes<OverseasRemittanceRes> processRemittanceOnce(@PathVariable Long userId, @RequestBody OverseasRemittanceReq request);

    /*
    REGULAR REMITTANCE
     */
    // 사용자 정기송금 설정 내역 조회
    @GetMapping(value = "/api/core/remittance/schedule")
    CoreBankingRes<List<RegularRemittanceResponseDto>> getScheduledRemittancesByUserId(@RequestParam("userId") Long userId);

    // 특정 정기송금의 세부사항 조회
    @GetMapping(value= "/api/core/remittance/schedule/{regRemId}")
    CoreBankingRes<RemittanceDetailDto> getScheduledRemittanceDetail(@RequestParam("userId") Long userId, @PathVariable("regRemId") Long regRemId);

    // 한 건의 정기 해외 송금 설정 내역 조회
    @GetMapping(value = "/api/core/remittance/schedule/history/{regRemId}")
    CoreBankingRes<List<RemittanceHistoryDto>> getRemittanceRecordsByRegRemId(@PathVariable("regRemId") Long regRemId, @RequestParam("userId") Long userId);

    // 단일 송금 내역 상세 조회
    @GetMapping(value = "/api/core/remittance/schedule/history/{regRemId}/{remittanceId}")
    CoreBankingRes<RemittanceHistoryDetailDto> getRemittanceRecordsByRegRemIdAndRemittanceId(
            @PathVariable("regRemId") Long regRemId,
            @PathVariable("remittanceId") Long remittanceId,
            @RequestParam("userId") Long userId);

    // 정기송금 신규 등록
    @PostMapping(value = "/api/core/remittance/schedule/add")
    void createScheduledRemittance(
            @RequestParam("userId") Long userId,
            @RequestBody RegularRemittanceCreateCoreDto dto
    );

    // 정기 해외 송금 내역 수정
    @PutMapping(value = "/api/core/remittance/schedule/{regRemId}")
    void updateScheduledRemittance(
            @PathVariable("regRemId") Long regRemId,
            @RequestParam("userId") Long userId,
            @RequestBody RegularRemittanceUpdateDto dto
    );

    // 정기 해외 송금 설정 삭제
    @DeleteMapping(value = "/api/core/remittance/schedule/{regRemId}")
    void cancelScheduledRemittance(
            @PathVariable("regRemId") Long regRemId,
            @RequestParam("userId") Long userId
    );

    /*
    EXCHANGE
     */
    /**
     * 특정 통화에 대한 환율 정보를 조회합니다.
     *
     * @param currency 조회할 통화 코드 (e.g., "USD")
     * @return 환율 정보가 담긴 응답 객체
     */
    @GetMapping(value = "/api/core/exchange/{currency}")
    CoreBankingRes<ExchangeRes> getExchangeRateByCurrency(
            @PathVariable("currency") String currency
    );
}
