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

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
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
    @GetMapping("/id/{accountId}")
    public ResponseEntity<?> getAccountByAccountId(
            @PathVariable("accountId") Long accountId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.getAccountByAccountId(accountId));
    }

    // 계좌 잔액 조회 by accountId
    @GetMapping("/id/{accountId}/balance")
    public ResponseEntity<?> getBalanceByAccountId(
            @PathVariable("accountId") Long accountId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.getAccountBalanceByAccountId(accountId));

    }

    // 계좌 조회 by accountNo
    @GetMapping("/number/{accountNo}")
    public ResponseEntity<?> getAccountByAccountNo(
            @PathVariable("accountNo") String accountNo
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.getAccountByAccountNo(accountNo));
    }

    // 전체 계좌 조회 by userId
    @GetMapping("/me/accounts")
    public ResponseEntity<?> getAccountByUserId(
            @ExternalUserId String userId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.getAccountByExternalUserId(userId));
    }

    // 거래 내역 조회 by accountId
    @GetMapping("/id/{accountId}/transactions")
    public ResponseEntity<?> getTransactionsByAccountId(
            @PathVariable("accountId")  Long accountId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, transactionService.getTransactionByAccountId(accountId));
    }

    // 일회성 해외 송금 등록
    @PostMapping("/{accountId}/remittance/once")
    public ResponseEntity<?> remittanceOnce(
            @ExternalUserId String userId,
            @PathVariable("accountId") Long accountId,
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
