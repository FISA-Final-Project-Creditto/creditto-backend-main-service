package org.creditto.creditto_service.global.infra.corebanking;

import org.creditto.creditto_service.domain.account.dto.CreateAccountReq;
import org.creditto.creditto_service.domain.overseasRemittance.dto.OverseasRemittanceReq;
import org.creditto.creditto_service.domain.remittance.dto.*;
import org.creditto.creditto_service.global.common.CoreBankingRes;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceRecordDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/api/core/account/{accountId}/account")
    CoreBankingRes<AccountRes> getAccountByAccountId(@PathVariable Long accountId);

    @GetMapping(value = "/api/core/account/{accountId}/balance")
    CoreBankingRes<BigDecimal> getAccountBalanceByAccountId(@PathVariable Long accountId);

    @GetMapping(value = "/api/core/account/{accountNo}")
    CoreBankingRes<AccountRes> getAccountByAccountNo(@PathVariable String accountNo);

    @GetMapping(value = "/api/core/account/client/{userId}")
    CoreBankingRes<List<AccountRes>> getAccountByUserId(@PathVariable Long userId);

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

    // 사용자 정기송금 설정 내역 조회
    @GetMapping("/api/core/remittance/schedule")
    List<RegularRemittanceResponseDto> getScheduledRemittancesByUserId(@RequestParam("userId") Long userId);

    // 한 건의 정기 해외 송금 설정 내역 조회
    @GetMapping("/api/core/remittance/schedule/{regRemId}")
    List<RemittanceHistoryDto> getRemittanceRecordsByRecurId(@PathVariable("regRemId") Long regRemId, @RequestParam("userId") Long userId);

    // 정기 해외 송금 기록의 내역 상세 조회
    @GetMapping("/api/core/remittance/{remittanceId}/detail")
    CoreBankingRes<RemittanceDetailDto> getRemittanceRecordsByRecurIdAndRemittanceId(
            @PathVariable("remittanceId") Long remittanceId,
            @RequestParam("userId") Long userId);

    // 정기송금 신규 등록
    @PostMapping("/api/core/remittance/schedule/add")
    void createScheduledRemittance(
            @RequestParam("userId") Long userId,
            @RequestBody RegularRemittanceCreateCoreDto dto
    );
//    List<RemittanceRecordDto> getRemittanceRecordsByRecurId(@PathVariable("recurId") Long recurId, @RequestParam("userId") Long userId);

    // 정기 해외 송금 내역 수정
    @PutMapping("/api/core/remittance/schedule/{regRemId}")
    void updateScheduledRemittance(
            @PathVariable("regRemId") Long regRemId,
            @RequestParam("userId") Long userId,
            @RequestBody RegularRemittanceUpdateDto dto
    );
//
//    // 정기 해외 송금 설정 삭제
//    @DeleteMapping("/api/core/remittance/schedule/{recurId}")
//    void cancelScheduledRemittance(
//            @PathVariable("recurId") Long recurId,
//            @RequestParam("userId") Long userId
//    );

    // 일회성 해외 송금  내역 조회
//    @GetMapping("/api/core/remmittance/{remittance}")
}
