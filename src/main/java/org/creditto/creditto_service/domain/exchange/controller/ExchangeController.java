package org.creditto.creditto_service.domain.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.exchange.service.ExchangeService;
import org.creditto.creditto_service.global.infra.corebanking.ExchangeRes;
import org.creditto.creditto_service.global.infra.corebanking.PreferentialRateRes;
import org.creditto.creditto_service.global.response.ApiResponseUtil;
import org.creditto.creditto_service.global.response.BaseResponse;
import org.creditto.creditto_service.global.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/{currency}")
    public ResponseEntity<BaseResponse<ExchangeRes>> getExchangeRateByCurrency(@PathVariable String currency) {
        return ApiResponseUtil.success(SuccessCode.OK, exchangeService.getExchangeRateByCurrency(currency));
    }

    @GetMapping("/preferential-rate/{userId}")
    public ResponseEntity<BaseResponse<PreferentialRateRes>> getPreferentialRateByUserId(@PathVariable Long userId) {
        return ApiResponseUtil.success(SuccessCode.OK, exchangeService.getPreferentialRateByUserId(userId));
    }
}
