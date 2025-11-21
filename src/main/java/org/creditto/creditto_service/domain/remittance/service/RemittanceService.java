package org.creditto.creditto_service.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceCreateRequestDto;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceResponseDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceRecordDto;
import org.creditto.creditto_service.global.infra.corebanking.CoreBankingFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RemittanceService {

    private final CoreBankingFeignClient coreBankingFeignClient;

    // 등록된 정기 해외 송금 설정 조회
    public List<RegularRemittanceResponseDto> getScheduledRemittanceList(String userId) {
        return coreBankingFeignClient.getScheduledRemittancesByUserId(userId);
    }

    // 정기 해외 송금 내역 상세 조회
    public List<RemittanceRecordDto> getScheduledRemittanceDetail(String userId, Long recurId) {
        return coreBankingFeignClient.getRemittanceRecordsByRecurId(recurId, userId);
    }

    // TODO: 정기 해외 송금 내역 신규 등록
    @Transactional
    public void createScheduledRemittance(String userId, RegularRemittanceCreateRequestDto regularRemittanceCreateRequestDto) {
        coreBankingFeignClient.createScheduledRemittance(userId, regularRemittanceCreateRequestDto);
    }

    // 정기 해외 송금 내역 수정
    @Transactional
    public void updateScheduledRemittance(String userId, Long recurId, RegularRemittanceResponseDto regularRemittanceResponseDto) {
        coreBankingFeignClient.updateScheduledRemittance(recurId, userId, regularRemittanceResponseDto);
    }

    // 정기 해외 송금 설정 삭제
    @Transactional
    public void cancelScheduledRemittance(String userId, Long recurId) {
        coreBankingFeignClient.cancelScheduledRemittance(recurId, userId);
    }
}
