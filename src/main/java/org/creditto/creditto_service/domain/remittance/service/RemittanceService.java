package org.creditto.creditto_service.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceCreateRequestDto;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceResponseDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceRecordDto;
import org.creditto.creditto_service.global.infra.corebanking.CoreBankingFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RemittanceService {

    private final CoreBankingFeignClient coreBankingFeignClient;

    // Task 1: 사용자 정기송금 설정 내역 조회
    public List<RegularRemittanceResponseDto> getScheduledRemittanceList(String userId) {
        log.info("{}", userId);
        return coreBankingFeignClient.getScheduledRemittancesByUserId(userId);
    }

    // 한 건의 정기 해외 송금 기록 상세 조회
    public List<RemittanceRecordDto> getScheduledRemittanceDetail(String userId, Long regRemId) {
        return coreBankingFeignClient.getRemittanceRecordsByRecurId(regRemId, userId);
    }

    // 정기 해외 송금 기록의 내역 상세 조회
    public RemittanceRecordDto getScheduledRemittanceRecordDetail(String userId, Long recurId, Long remittanceId) {
        return coreBankingFeignClient.getRemittanceRecordsByRecurIdAndRemittanceId(recurId, remittanceId, userId);
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
