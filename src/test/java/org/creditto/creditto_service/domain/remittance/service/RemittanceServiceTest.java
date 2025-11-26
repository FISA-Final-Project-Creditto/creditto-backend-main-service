package org.creditto.creditto_service.domain.remittance.service;

import org.creditto.creditto_service.domain.remittance.dto.*;
import org.creditto.creditto_service.global.common.CoreBankingRes;
import org.creditto.creditto_service.global.infra.corebanking.CoreBankingFeignClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RemittanceServiceTest {

    @InjectMocks
    private RemittanceService remittanceService;

    @Mock
    private CoreBankingFeignClient coreBankingFeignClient;

    @Test
    @DisplayName("정기 송금 리스트 조회")
    void getScheduledRemittanceListTest() {
        // given
        Long userId = 1L;
        RegularRemittanceResponseDto mockDto = RegularRemittanceResponseDto.builder().build();
        List<RegularRemittanceResponseDto> expectedResponse = Collections.singletonList(mockDto);
        given(coreBankingFeignClient.getScheduledRemittancesByUserId(userId)).willReturn(expectedResponse);

        // when
        List<RegularRemittanceResponseDto> result = remittanceService.getScheduledRemittanceList(userId);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedResponse, result);
        verify(coreBankingFeignClient).getScheduledRemittancesByUserId(userId);
    }

    @Test
    @DisplayName("하나의 정기송금 설정에 대한 송금 기록 조회")
    void getScheduledRemittanceDetailTest() {
        // given
        Long userId = 1L;
        Long regRemId = 101L;

        RemittanceHistoryDto history1 = RemittanceHistoryDto.builder()
                .sendAmount(new BigDecimal("100.00"))
                .exchangeRate(new BigDecimal("1200.00"))
                .createdDate(LocalDate.of(2023, 1, 1))
                .build();
        RemittanceHistoryDto history2 = RemittanceHistoryDto.builder()
                .sendAmount(new BigDecimal("200.00"))
                .exchangeRate(new BigDecimal("1210.00"))
                .createdDate(LocalDate.of(2023, 1, 15))
                .build();
        RemittanceHistoryDto history3 = RemittanceHistoryDto.builder()
                .sendAmount(new BigDecimal("300.00"))
                .exchangeRate(new BigDecimal("1220.00"))
                .createdDate(LocalDate.of(2023, 2, 1))
                .build();

        List<RemittanceHistoryDto> expectedResponse = List.of(history1, history2, history3);
        given(coreBankingFeignClient.getRemittanceRecordsByRecurId(regRemId, userId)).willReturn(expectedResponse);

        // when
        List<RemittanceHistoryDto> result = remittanceService.getScheduledRemittanceDetail(userId, regRemId);

        // then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedResponse, result);
        verify(coreBankingFeignClient).getRemittanceRecordsByRecurId(regRemId, userId);
    }

    @Test
    @DisplayName("정기 해외 송금 기록의 내역 상세 조회")
    void getScheduledRemittanceRecordDetailTest() {
        // given
        Long userId = 1L;
        Long remittanceId = 201L;
        RemittanceDetailDto mockDto = RemittanceDetailDto.builder().build();
        CoreBankingRes<RemittanceDetailDto> coreBankingResponse = new CoreBankingRes<>(200, "Success", mockDto);
        given(coreBankingFeignClient.getRemittanceRecordsByRecurIdAndRemittanceId(remittanceId, userId)).willReturn(coreBankingResponse);

        // when
        RemittanceDetailDto result = remittanceService.getScheduledRemittanceRecordDetail(userId, remittanceId);

        // then
        assertNotNull(result);
        assertEquals(mockDto, result);
        verify(coreBankingFeignClient).getRemittanceRecordsByRecurIdAndRemittanceId(remittanceId, userId);
    }

    @Test
    @DisplayName("정기송금 신규 등록")
    void createScheduledRemittanceTest() {
        // given
        Long userId = 1L;
        RegularRemittanceCreateDto createDto = new RegularRemittanceCreateDto();
        // Assume RegularRemittanceCreateDto needs some data, if not, this can be simplified
        createDto.setAccountNo("123-456");
        createDto.setSendAmount(new BigDecimal("100"));

        // when
        remittanceService.createScheduledRemittance(userId, createDto);

        // then
        verify(coreBankingFeignClient, times(1)).createScheduledRemittance(eq(userId), any(RegularRemittanceCreateCoreDto.class));
    }

    @Test
    @DisplayName("정기 해외 송금 내역 수정")
    void updateScheduledRemittanceTest() {
        // given
        Long userId = 1L;
        Long regRemId = 101L;
        RegularRemittanceUpdateDto updateDto = new RegularRemittanceUpdateDto();
        updateDto.setSendAmount(new BigDecimal("200"));


        // when
        remittanceService.updateScheduledRemittance(userId, regRemId, updateDto);

        // then
        verify(coreBankingFeignClient, times(1)).updateScheduledRemittance(regRemId, userId, updateDto);
    }

    @Test
    @DisplayName("정기 해외 송금 설정 삭제")
    void cancelScheduledRemittanceTest() {
        // given
        Long userId = 1L;
        Long regRemId = 101L;

        // when
        remittanceService.cancelScheduledRemittance(userId, regRemId);

        // then
        verify(coreBankingFeignClient, times(1)).cancelScheduledRemittance(regRemId, userId);
    }
}