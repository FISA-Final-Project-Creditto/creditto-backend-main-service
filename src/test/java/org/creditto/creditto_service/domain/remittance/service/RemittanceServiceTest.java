package org.creditto.creditto_service.domain.remittance.service;

import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceResponseDto;
import org.creditto.creditto_service.global.infra.corebanking.CoreBankingFeignClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RemittanceServiceTest {

    @InjectMocks
    private RemittanceService remittanceService;

    @Mock
    private CoreBankingFeignClient coreBankingFeignClient;

    @Test
    @DisplayName("정기 송금 리스트 조회 성공")
    void getScheduledRemittanceList_success() {
        // given
        Long userId = 1111L;
        RegularRemittanceResponseDto mockDto = RegularRemittanceResponseDto.builder()
                .regRemId(1L)
                .receivedCurrency("USD")
                .sendAmount(new BigDecimal("1000.00"))
                .regRemStatus("ACTIVE")
                .regRemType("매주")
                .scheduledDay("FRIDAY")
                .build();
        List<RegularRemittanceResponseDto> coreBankingResponse = Collections.singletonList(mockDto);

        given(coreBankingFeignClient.getScheduledRemittancesByUserId(userId))
                .willReturn(coreBankingResponse);

        // when
        List<RegularRemittanceResponseDto> result = remittanceService.getScheduledRemittanceList(userId);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(coreBankingResponse, result);
        assertEquals(mockDto.getRegRemId(), result.get(0).getRegRemId());
        assertEquals(mockDto.getSendAmount(), result.get(0).getSendAmount());

        verify(coreBankingFeignClient).getScheduledRemittancesByUserId(userId);
    }
}
