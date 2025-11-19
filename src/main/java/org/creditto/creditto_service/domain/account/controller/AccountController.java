package org.creditto.creditto_service.domain.account.controller;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.account.dto.CreateAccountReq;
import org.creditto.creditto_service.domain.account.service.AccountService;
import org.creditto.creditto_service.domain.overseasRemittance.dto.OverseasRemittanceReq;
import org.creditto.creditto_service.domain.overseasRemittance.service.OverseasRemittanceService;
import org.creditto.creditto_service.domain.transaction.service.TransactionService;
import org.creditto.creditto_service.global.infra.corebanking.AccountRes;
import org.creditto.creditto_service.global.resolver.ExternalUserId;
import org.creditto.creditto_service.global.response.ApiResponseUtil;
import org.creditto.creditto_service.global.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts/open")
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final OverseasRemittanceService overseasRemittanceService;

    @PostMapping
    public ResponseEntity<?> createAccount(
            @ExternalUserId String userId,
            @RequestBody CreateAccountReq request
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.createAccount(userId, request));
    }

    // 계좌 조회 by accountId
    @GetMapping("/{accountId}/account")
    public ResponseEntity<?> getAccountByAccountId(
            @PathVariable("accountId") Long accountId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.getAccountByAccountId(accountId));
    }

    // 계좌 잔액 조회 by accountId
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<?> getBalanceByAccountId(
            @PathVariable("accountId") Long accountId
    ) {
        AccountRes accountRes = accountService.getAccountByAccountId(accountId);
        return ApiResponseUtil.success(SuccessCode.OK, accountRes.balance());
    }

    // 계좌 조회 by accountNo
    @GetMapping("/{accountNo}")
    public ResponseEntity<?> getAccountByAccountNo(
            @PathVariable("accountNo") String accountNo
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.getAccountByAccountNo(accountNo));
    }

    // 계좌 조회 by userId
    @GetMapping("/{userId}/account")
    public ResponseEntity<?> getAccountByUserId(
            @ExternalUserId String userId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.getAccountByExternalUserId(userId));
    }

    // 거래 내역 조회 by accountId
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<?> getTransactionsByAccountId(
            @PathVariable("accountId")  Long accountId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, transactionService.getTransactionByAccountId(accountId));
    }

    // 일회성 해외 송금 등록
    @PostMapping("/{accountId}/remittance/once")
    public ResponseEntity<?> remittanceOnce(
            @ExternalUserId String userId,
            @RequestBody OverseasRemittanceReq request
            ) {
        return ApiResponseUtil.success(SuccessCode.OK, overseasRemittanceService.processRemittanceOnce(userId, request));
    }

//    // 정기 해외 송금 등록
//    @PostMapping("/{accountId}/remittance/schedule")
//    public ResponseEntity<?> remittanceSchedule(
//
//    ) {
//
//    }

}
