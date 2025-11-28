package org.creditto.creditto_service.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.remittance.dto.*;
import org.creditto.creditto_service.global.infra.corebanking.CoreBankingFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RemittanceService {

    private final CoreBankingFeignClient coreBankingFeignClient;

    // Task 1: 사용자 정기송금 설정 내역 조회
    public List<RegularRemittanceResponseDto> getScheduledRemittanceList(Long userId) {
        return coreBankingFeignClient.getScheduledRemittancesByUserId(userId).data();
    }

    // Task 1-2: 특정 정기송금의 세부사항 조회
    public RemittanceDetailDto getScheduledRemittanceDetail(Long userId, Long regRemId) {
        return coreBankingFeignClient.getScheduledRemittanceDetail(userId, regRemId).data();
    }

    // Task 2: 하나의 정기송금 설정에 대한 송금 기록 조회
    public List<RemittanceHistoryDto> getScheduledRemittanceHistory(Long userId, Long regRemId) {
        return coreBankingFeignClient.getRemittanceRecordsByRegRemId(regRemId, userId).data();
    }

    // Task 3: 정기 해외 송금 기록의 내역 상세 조회
    public RemittanceHistoryDetailDto getScheduledRemittanceHistoryDetail(Long userId, Long regRemId, Long remittanceId) {
        return coreBankingFeignClient.getRemittanceRecordsByRegRemIdAndRemittanceId(regRemId, remittanceId, userId).data();
    }

    // Task 4: 정기송금 신규 등록
    @Transactional
    public void createScheduledRemittance(Long userId, RegularRemittanceCreateDto dto) {
        RegularRemittanceCreateCoreDto regularRemittanceCreateCoreDto = RegularRemittanceCreateCoreDto.from(dto);
        coreBankingFeignClient.createScheduledRemittance(userId, regularRemittanceCreateCoreDto);
    }

    // Task 5: 정기 해외 송금 내역 수정
    @Transactional
    public void updateScheduledRemittance(Long userId, Long regRemId, RegularRemittanceUpdateDto dto) {
        coreBankingFeignClient.updateScheduledRemittance(regRemId, userId, dto);
    }

    // 정기 해외 송금 설정 삭제
    @Transactional
    public void cancelScheduledRemittance(Long userId, Long regRemId) {
        coreBankingFeignClient.cancelScheduledRemittance(regRemId, userId);
    }
}
