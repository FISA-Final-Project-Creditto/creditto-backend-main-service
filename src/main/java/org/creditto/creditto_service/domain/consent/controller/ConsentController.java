package org.creditto.creditto_service.domain.consent.controller;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.consent.dto.ConsentDefinitionRes;
import org.creditto.creditto_service.domain.consent.service.ConsentService;
import org.creditto.creditto_service.global.response.ApiResponseUtil;
import org.creditto.creditto_service.global.response.BaseResponse;
import org.creditto.creditto_service.global.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * 특정 ID의 동의서 정의를 조회
     *
     * @param definitionId 조회할 동의서의 ID
     * @return 특정 동의서의 정보를 포함하는 응답 엔티티
     */
    @GetMapping("/definitions/{definitionId}")
    public ResponseEntity<BaseResponse<ConsentDefinitionRes>> getConsentDefinitionById(@PathVariable Long definitionId) {
        ConsentDefinitionRes definition = consentService.getConsentDefinition(definitionId);
        return ApiResponseUtil.success(SuccessCode.OK, definition);
    }
}