package org.creditto.creditto_service.domain.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.creditto.creditto_service.domain.account.dto.CreateAccountReq;
import org.creditto.creditto_service.domain.account.service.AccountService;
import org.creditto.creditto_service.domain.overseasRemittance.dto.OverseasRemittanceReq;
import org.creditto.creditto_service.domain.overseasRemittance.service.OverseasRemittanceService;
import org.creditto.creditto_service.domain.transaction.service.TransactionService;
import org.creditto.creditto_service.global.infra.corebanking.AccountRes;
import org.creditto.creditto_service.global.infra.corebanking.OverseasRemittanceRes;
import org.creditto.creditto_service.global.infra.corebanking.TransactionRes;
import org.creditto.creditto_service.global.resolver.UserId;
import org.creditto.creditto_service.global.response.ApiResponseUtil;
import org.creditto.creditto_service.global.response.BaseResponse;
import org.creditto.creditto_service.global.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final OverseasRemittanceService overseasRemittanceService;

    // 계좌 개설
    @PostMapping
    public ResponseEntity<BaseResponse<AccountRes>> createAccount(
            @UserId Long userId,
            @RequestBody CreateAccountReq request
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.createAccount(userId, request));
    }

    // 계좌 조회 by accountId
    @GetMapping("/id/{accountId}")
    public ResponseEntity<BaseResponse<AccountRes>> getAccountByAccountId(
            @PathVariable("accountId") Long accountId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.getAccountByAccountId(accountId));
    }

    // 계좌 잔액 조회 by accountId
    @GetMapping("/id/{accountId}/balance")
    public ResponseEntity<BaseResponse<BigDecimal>> getBalanceByAccountId(
            @PathVariable("accountId") Long accountId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.getAccountBalanceByAccountId(accountId));

    }

    // 계좌 조회 by accountNo
    @GetMapping("/number/{accountNo}")
    public ResponseEntity<BaseResponse<AccountRes>> getAccountByAccountNo(
            @PathVariable("accountNo") String accountNo
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.getAccountByAccountNo(accountNo));
    }

    // 전체 계좌 조회 by userId
    @GetMapping("/me/accounts")
    public ResponseEntity<BaseResponse<List<AccountRes>>> getAccountByUserId(
            @UserId Long userId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, accountService.getAccountByUserId(userId));
    }

    // 거래 내역 조회 by accountId
    @GetMapping("/id/{accountId}/transactions")
    public ResponseEntity<BaseResponse<List<TransactionRes>>> getTransactionsByAccountId(
            @PathVariable("accountId")  Long accountId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, transactionService.getTransactionByAccountId(accountId));
    }

    // 일회성 해외 송금 등록
    @PostMapping("/{accountId}/remittance/once")
    public ResponseEntity<BaseResponse<OverseasRemittanceRes>> remittanceOnce(
            @UserId Long userId,
            @PathVariable("accountId") Long accountId,
            @RequestBody OverseasRemittanceReq request
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, overseasRemittanceService.processRemittanceOnce(userId, accountId, request));
    }

}
