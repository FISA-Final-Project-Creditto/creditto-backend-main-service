package org.creditto.creditto_service.domain.admin.dto;

import lombok.Builder;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceResponseDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceDetailDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceHistoryDetailDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceHistoryDto;

import java.util.Collections;
import java.util.List;

@Builder
public record RemittanceDashboardView(
        Long searchUserId,
        Long selectedRemittanceId,
        Long historyRemittanceId,
        List<RegularRemittanceResponseDto> remittances,
        RemittanceSummaryView summary,
        RemittanceDetailDto remittanceDetail,
        List<RemittanceHistoryDto> historyRecords,
        RemittanceHistoryDetailDto historyDetail,
        String errorMessage
) {
    public static RemittanceDashboardView empty(Long searchUserId) {
        return RemittanceDashboardView.builder()
                .searchUserId(searchUserId)
                .remittances(Collections.emptyList())
                .summary(RemittanceSummaryView.empty())
                .historyRecords(Collections.emptyList())
                .build();
    }
}
