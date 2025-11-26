package org.creditto.creditto_service.domain.consent.controller;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.consent.dto.ConsentDefinitionRes;
import org.creditto.creditto_service.domain.consent.dto.ConsentRecordRes;
import org.creditto.creditto_service.domain.consent.service.ConsentService;
import org.creditto.creditto_service.global.resolver.UserId;
import org.creditto.creditto_service.global.response.ApiResponseUtil;
import org.creditto.creditto_service.global.response.BaseResponse;
import org.creditto.creditto_service.global.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 동의서 관련 API 요청을 처리하는 컨트롤러
 * 클라이언트의 동의 및 철회, 동의서 조회 등의 기능을 제공
 */
@RestController
@RequestMapping("/api/consents")
@RequiredArgsConstructor
public class ConsentController {

    private final ConsentService consentService;

    /**
     * 모든 동의서의 최신 버전을 조회
     *
     * @return 최신 버전의 모든 동의서 목록을 포함하는 응답 엔티티
     */
    @GetMapping("/definitions")
    public ResponseEntity<BaseResponse<List<ConsentDefinitionRes>>> getLatestConsentDefinitions() {
        List<ConsentDefinitionRes> definitions = consentService.getLatestConsentDefinitions();
        return ApiResponseUtil.success(SuccessCode.OK, definitions);
    }

    /**
     * 사용자가 특정 동의서에 동의
    */
    @PostMapping("/agree")
    public ResponseEntity<BaseResponse<ConsentRecordRes>> agreeConsent(
            @UserId Long userId,
            @RequestParam String consentCode
    ) {
        return ApiResponseUtil.success(SuccessCode.CREATED, consentService.agree(userId, consentCode));
    }

    /**
     * 사용자가 동의했던 내역을 철회
     */
    @PostMapping("/withdraw")
    public ResponseEntity<BaseResponse<Void>> withdrawConsent(
            @UserId Long userId,
            @RequestParam(name = "consentCode") String consentCode
    ) {
        consentService.withdraw(userId, consentCode);
        return ApiResponseUtil.success(SuccessCode.OK);
    }

    /**
     * 특정 사용자의 전체 동의 내역을 조회
     */
    @GetMapping("/record")
    public ResponseEntity<BaseResponse<List<ConsentRecordRes>>> getConsentRecord(@UserId Long userId) {
        return ApiResponseUtil.success(SuccessCode.OK, consentService.getConsentRecord(userId));
    }

    /**
     * 특정 사용자가 특정 동의서의 최신 버전에 동의했는지 확인
     */
    @GetMapping("/check/{code}")
    public ResponseEntity<BaseResponse<Boolean>> checkAgreement(
            @UserId Long userId,
            @PathVariable String code
    ) {
        boolean hasAgreed = consentService.checkAgreement(userId, code);
        return ApiResponseUtil.success(SuccessCode.OK, hasAgreed);
    }
}