package org.creditto.creditto_service.global.infra.corebanking;

import org.creditto.creditto_service.domain.account.dto.CreateAccountReq;
import org.creditto.creditto_service.domain.overseasRemittance.dto.OverseasRemittanceReq;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceDto;
import org.creditto.creditto_service.global.common.CoreBankingRes;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceCreateRequestDto;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceResponseDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceRecordDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "core-banking",
        url = "${CORE_BANKING_SERVER_URL}"
)
public interface CoreBankingFeignClient {
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

    @GetMapping(value = "/api/core/transactions/{accountId}")
    CoreBankingRes<List<TransactionRes>> getTransactionByAccountId(@PathVariable Long accountId);

    @PostMapping(value = "/api/core/remittance/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    CoreBankingRes<OverseasRemittanceRes> processRemittanceOnce(@PathVariable Long userId, @RequestBody OverseasRemittanceReq request);

    // Task 1: 사용자 정기송금 설정 내역 조회
    @GetMapping("/api/core/remittance/schedule")
    List<RegularRemittanceDto> getScheduledRemittancesByUserId(@RequestParam("userId") String userId);

    // 한 건의 정기 해외 송금 설정 내역 조회
    @GetMapping("/api/core/remittance/schedule/{regRemId}")
    List<RemittanceRecordDto> getRemittanceRecordsByRecurId(@PathVariable("regRemId") Long regRemId, @RequestParam("userId") String userId);

    // 정기 해외 송금 기록의 내역 상세 조회
    @GetMapping("/api/core/remittance/schedule/{regRemId}/{remittanceId}")
    RemittanceRecordDto getRemittanceRecordsByRecurIdAndRemittanceId(
            @PathVariable("regRemId") Long regRemId,
            @PathVariable("remittanceId") Long remittanceId,
            @RequestParam("userId") String userId);


    // TODO: 정기 해외 송금 내역 신규 등록
    @PostMapping("/api/core/remittance/schedule")
    void createScheduledRemittance(
            @RequestParam("userId") String userId,
            @RequestBody RegularRemittanceCreateRequestDto regularRemittanceCreateRequestDto
    );
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

    // 일회성 해외 송금  내역 조회
//    @GetMapping("/api/core/remmittance/{remittance}")
}
