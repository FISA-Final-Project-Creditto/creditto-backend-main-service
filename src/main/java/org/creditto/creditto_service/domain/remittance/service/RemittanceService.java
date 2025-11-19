package org.creditto.creditto_service.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceDto;
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
    public List<RegularRemittanceDto> getScheduledRemittanceList(String userId) {
        return coreBankingFeignClient.getScheduledRemittancesByUserId(userId);
    }

    // 정기 해외 송금 내역 상세 조회
    public List<RemittanceRecordDto> getScheduledRemittanceDetail(String userId, String recurId) {
        return coreBankingFeignClient.getRemittanceRecordsByRecurId(recurId, userId);
    }

    // 정기 해외 송금 내역 수정
    @Transactional
    public void updateScheduledRemittance(String userId, String recurId, RegularRemittanceDto regularRemittanceDto) {
        coreBankingFeignClient.updateScheduledRemittance(recurId, userId, regularRemittanceDto);
    }

    // 정기 해외 송금 설정 삭제
    @Transactional
    public void cancelScheduledRemittance(String userId, String recurId) {
        coreBankingFeignClient.cancelScheduledRemittance(recurId, userId);
    }
}
