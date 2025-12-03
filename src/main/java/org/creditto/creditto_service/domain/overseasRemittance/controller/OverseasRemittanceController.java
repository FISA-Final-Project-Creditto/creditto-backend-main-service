package org.creditto.creditto_service.domain.overseasRemittance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.overseasRemittance.dto.OverseasRemittanceReq;
import org.creditto.creditto_service.domain.overseasRemittance.service.OverseasRemittanceService;
import org.creditto.creditto_service.global.infra.corebanking.OverseasRemittanceRes;
import org.creditto.creditto_service.global.resolver.UserId;
import org.creditto.creditto_service.global.response.ApiResponseUtil;
import org.creditto.creditto_service.global.response.BaseResponse;
import org.creditto.creditto_service.global.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/remittance")
public class OverseasRemittanceController {

    private final OverseasRemittanceService overseasRemittanceService;

    // 일회성 해외 송금 등록
    @PostMapping("/once")
    public ResponseEntity<BaseResponse<OverseasRemittanceRes>> remittanceOnce(
            @UserId Long userId,
            @Valid @RequestBody OverseasRemittanceReq request
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, overseasRemittanceService.processRemittanceOnce(userId, request));
    }

    // 해외 송금 내역 조회
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<List<OverseasRemittanceRes>>> getRemittanceList (
            @PathVariable Long userId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, overseasRemittanceService.getRemittanceList(userId));
    }
}
