package org.creditto.creditto_service.domain.remittance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceCreateRequestDto;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceResponseDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceRecordDto;
import org.creditto.creditto_service.domain.remittance.service.RemittanceService;
import org.creditto.creditto_service.global.resolver.ExternalUserId;
import org.creditto.creditto_service.global.response.ApiResponseUtil;
import org.creditto.creditto_service.global.response.BaseResponse;
import org.creditto.creditto_service.global.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/remittance")
public class RemittanceController {

    private final RemittanceService remittanceService;

    /*
     * Task 1: 사용자 정기송금 설정 내역 조회
     *
     * @param ExternalUserId 등록된 정기 송금 설정을 조회할 고객의 ID
     * @return 해당 고객의 정기 송금 설정 리스트 ({@link RegularRemittanceResponseDto})
     */
    @GetMapping("/scheduled")
    public ResponseEntity<BaseResponse<List<RegularRemittanceResponseDto>>> getScheduledRemittanceList (
            @ExternalUserId String userId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, remittanceService.getScheduledRemittanceList(userId));
    }

    /*
     * 한 건의 정기 해외 송금 기록 상세 조회
     *
     * @param userId 정기 송금 내역을 조회할 고객의 ID
     * @param recurId 조회할 정기 송금 설정의 ID
     * @return 해당 정기 송금 설정의 송금 내역 리스트 ({@link RemittanceRecordDto})
     */
    @GetMapping("/scheduled/{regRemId}")
    public ResponseEntity<BaseResponse<List<RemittanceRecordDto>>> getScheduledRemittanceDetail (
            @ExternalUserId String userId,
            @PathVariable Long regRemId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, remittanceService.getScheduledRemittanceDetail(userId, regRemId));
    }

    /*
     * 정기 해외 송금 기록의 내역 상세 조회
     *
     * @param userId 정기 송금 내역을 조회할 고객의 ID
     * @param recurId 조회할 정기 송금 설정의 ID
     * @param remittanceId 선택한 정기 송금
     * @return 해당 정기 송금 설정의 송금 내역 리스트 ({@link RemittanceRecordDto})
     */
    @GetMapping("/scheduled/{recurId}/{remittanceId}")
    public ResponseEntity<BaseResponse<RemittanceRecordDto>> getScheduledRemittanceRecordDetail (
            @ExternalUserId String userId,
            @PathVariable Long recurId,
            @PathVariable Long remittanceId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, remittanceService.getScheduledRemittanceRecordDetail(userId, recurId, remittanceId));
    }

    /* 정기 해외 송금 설정 신규 등록
     *
     * @param userId 정기 송금 내역을 등록할 고객의 ID
     * @return 성공 응답
     */
    @PostMapping("/scheduled")
    public ResponseEntity<BaseResponse<Void>> createScheduledRemittance (
            @ExternalUserId String userId,
            @RequestBody RegularRemittanceCreateRequestDto regularRemittanceCreateRequestDto
    ) {
        remittanceService.createScheduledRemittance(userId, regularRemittanceCreateRequestDto);
        log.info("Controller:{}", regularRemittanceCreateRequestDto);
        return ApiResponseUtil.success(SuccessCode.OK, null);
    }

    /*
     * 정기 해외 송금 설정 수정
     *
     * @param userId 정기 송금 설정을 수정할 고객의 ID
     * @param recurId 수정할 정기 송금 설정의 ID
     * @param regularRemittanceResponseDto 수정할 정기 송금 설정 정보
     * @return 성공 응답
     */
    @PutMapping("/scheduled/{recurId}")
    public ResponseEntity<BaseResponse<Void>> updateScheduledRemittance (
            @ExternalUserId String userId,
            @PathVariable Long recurId,
            @RequestBody RegularRemittanceResponseDto regularRemittanceResponseDto
    ) {
        remittanceService.updateScheduledRemittance(userId, recurId, regularRemittanceResponseDto);
        return ApiResponseUtil.success(SuccessCode.OK, null);
    }

    /*
     * 정기 해외 송금 설정 삭제
     *
     * @param userId 정기 해외 송금 설정을 삭제할 고객의 ID
     * @param recurId 삭제할 정기 해외 송금 설정의 ID
     * @return 성공 응답
     */
    @DeleteMapping("/scheduled/{recurId}")
    public ResponseEntity<BaseResponse<Void>> cancelScheduledRemittance (
            @ExternalUserId String userId,
            @PathVariable Long recurId
    ) {
        remittanceService.cancelScheduledRemittance(userId, recurId);
        return ApiResponseUtil.success(SuccessCode.OK, null);
    }

    // 일회성 송금 내역 조회, 전체 송금 내역 조회는 추후 구현


}
