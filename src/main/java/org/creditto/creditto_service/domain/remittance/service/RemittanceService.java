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
        return coreBankingFeignClient.getScheduledRemittancesByUserId(userId);
    }

    // Task 2: 하나의 정기송금 설정에 대한 송금 기록 조회
    public List<RemittanceHistoryDto> getScheduledRemittanceDetail(Long userId, Long regRemId) {
        return coreBankingFeignClient.getRemittanceRecordsByRecurId(regRemId, userId);
    }

    // Task 3: 정기 해외 송금 기록의 내역 상세 조회
    public RemittanceDetailDto getScheduledRemittanceRecordDetail(Long userId, Long remittanceId) {
        return coreBankingFeignClient.getRemittanceRecordsByRecurIdAndRemittanceId(remittanceId, userId).data();
    }

    // Task 4: 정기송금 신규 등록
    @Transactional
    public void createScheduledRemittance(Long userId, RegularRemittanceCreateDto dto) {
        // TODO: 인증 서버에 송금인 관련 정보 따로 저장

        RegularRemittanceCreateCoreDto regularRemittanceCreateCoreDto = RegularRemittanceCreateCoreDto.builder()
                .accountNo(dto.getAccountNo())
                .sendCurrency(dto.getSendCurrency())
                .receiveCurrency(dto.getReceiveCurrency())
                .sendAmount(dto.getSendAmount())
                .regRemType(dto.getRegRemType())
                .scheduledDate(dto.getScheduledDate())
                .scheduledDay(dto.getScheduledDay())
                .recipientName(dto.getRecipientName())
                .recipientPhoneCc(dto.getRecipientPhoneCc())
                .recipientPhoneNo(dto.getRecipientPhoneNo())
                .recipientAddress(dto.getRecipientAddress())
                .recipientCountry(dto.getRecipientCountry())
                .recipientBankName(dto.getRecipientBankName())
                .recipientBankCode(dto.getRecipientBankCode())
                .recipientAccountNo(dto.getRecipientAccountNo())
                .build();

        coreBankingFeignClient.createScheduledRemittance(userId, regularRemittanceCreateCoreDto);
    }

    // Task 5: 정기 해외 송금 내역 수정
    @Transactional
    public void updateScheduledRemittance(Long userId, Long regRemId, RegularRemittanceUpdateDto dto) {
        coreBankingFeignClient.updateScheduledRemittance(regRemId, userId, dto);
    }

    // Task 6: 정기 해외 송금 설정 삭제
    @Transactional
    public void cancelScheduledRemittance(Long userId, Long regRemId) {
        coreBankingFeignClient.cancelScheduledRemittance(regRemId, userId);
    }
}
