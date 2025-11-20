package org.creditto.creditto_service.domain.remittance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.remittance.dto.*;
import org.creditto.creditto_service.domain.remittance.service.RemittanceService;
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
public class RemittanceController {

    private final RemittanceService remittanceService;

    /*
     * 사용자 정기송금 설정 내역 조회
     *
     * @param ExternalUserId 등록된 정기 송금 설정을 조회할 고객의 ID
     * @return 해당 고객의 정기 송금 설정 리스트 ({@link RegularRemittanceResponseDto})
     */
    @GetMapping
    public ResponseEntity<BaseResponse<List<RegularRemittanceResponseDto>>> getScheduledRemittanceList (
            @UserId Long userId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, remittanceService.getScheduledRemittanceList(userId));
    }

    /*
     * 하나의 정기송금 설정에 대한 송금 기록 조회
     *
     * @param userId 정기 송금 내역을 조회할 고객의 ID
     * @param recurId 조회할 정기 송금 설정의 ID
     * @return 해당 정기 송금 설정의 송금 내역 리스트 ({@link RemittanceRecordDto})
     */
    @GetMapping("/{regRemId}")
    public ResponseEntity<BaseResponse<List<RemittanceHistoryDto>>> getScheduledRemittanceDetail (
            @UserId Long userId,
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
     * @return 해당 정기 송금 설정의 송금 내역 상세 ({@link RemittanceDetailDto})
     */
    @GetMapping("/{remittanceId}/detail")
    public ResponseEntity<BaseResponse<RemittanceDetailDto>> getScheduledRemittanceRecordDetail (
            @UserId Long userId,
            @PathVariable Long remittanceId
    ) {
        return ApiResponseUtil.success(SuccessCode.OK, remittanceService.getScheduledRemittanceRecordDetail(userId, remittanceId));
    }

    /* 정기송금 신규 등록
     *
     * @param userId 정기 송금 내역을 등록할 고객의 ID
     * @return 성공 응답
     */
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<Void>> createScheduledRemittance (
            @UserId Long userId,
            @RequestBody @Valid RegularRemittanceCreateDto dto
    ) {
        remittanceService.createScheduledRemittance(userId, dto);
//        log.info("Controller:{}", regularRemittanceCreateRequestDto);
        return ApiResponseUtil.success(SuccessCode.OK, null);
    }

    /*
     * 정기 해외 송금 설정 수정
     *
     * @param userId 정기 송금 설정을 수정할 고객의 ID
     * @param regRemId 수정할 정기 송금 설정의 ID
     * @param RegularRemittanceUpdateDto 수정할 정기 송금 설정 정보
     * @return 성공 응답
     */
    @PutMapping("/{regRemId}")
    public ResponseEntity<BaseResponse<Void>> updateScheduledRemittance (
            @UserId Long userId,
            @PathVariable Long regRemId,
            @RequestBody RegularRemittanceUpdateDto dto
    ) {
        remittanceService.updateScheduledRemittance(userId, regRemId, dto);
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
            @UserId Long userId,
            @PathVariable Long regRemId
    ) {
        remittanceService.cancelScheduledRemittance(userId, regRemId);
        return ApiResponseUtil.success(SuccessCode.OK, null);
    }

    // 일회성 송금 내역 조회, 전체 송금 내역 조회는 추후 구현


}
