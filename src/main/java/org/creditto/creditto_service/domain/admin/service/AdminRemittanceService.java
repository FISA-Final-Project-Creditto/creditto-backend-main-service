package org.creditto.creditto_service.domain.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.creditto.creditto_service.domain.admin.dto.RemittanceDashboardView;
import org.creditto.creditto_service.domain.admin.dto.RemittanceSummaryView;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceResponseDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceDetailDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceHistoryDetailDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceHistoryDto;
import org.creditto.creditto_service.domain.remittance.service.RemittanceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.creditto.creditto_service.global.common.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminRemittanceService {

    private final RemittanceService remittanceService;

    public RemittanceDashboardView buildDashboard(Long userId, Long regRemId, Long remittanceId) {
        if (userId == null) {
            return RemittanceDashboardView.empty(null);
        }

        List<RegularRemittanceResponseDto> remittances = remittanceService.getScheduledRemittanceList(userId);
        RemittanceSummaryView summary = buildSummary(remittances);

        RemittanceDashboardView.RemittanceDashboardViewBuilder builder = RemittanceDashboardView.builder()
                .searchUserId(userId)
                .selectedRemittanceId(regRemId)
                .historyRemittanceId(remittanceId)
                .remittances(remittances)
                .summary(summary);

        if (regRemId == null) {
            return builder.build();
        }

        try {
            RemittanceDetailDto detail = remittanceService.getScheduledRemittanceDetail(userId, regRemId);
            builder.remittanceDetail(detail);

            List<RemittanceHistoryDto> history = remittanceService.getScheduledRemittanceHistory(userId, regRemId);
            builder.historyRecords(history);

            if (remittanceId != null) {
                RemittanceHistoryDetailDto historyDetail =
                        remittanceService.getScheduledRemittanceHistoryDetail(userId, regRemId, remittanceId);
                builder.historyDetail(historyDetail);
            }
        } catch (Exception ex) {
            log.warn("Failed to load remittance detail. userId={}, regRemId={}", userId, regRemId, ex);
            builder.errorMessage(ex.getMessage());
        }

        return builder.build();
    }

    private RemittanceSummaryView buildSummary(List<RegularRemittanceResponseDto> remittances) {
        if (remittances == null || remittances.isEmpty()) {
            return RemittanceSummaryView.empty();
        }

        long activeCount = remittances.stream()
                .filter(dto -> ACTIVE.equalsIgnoreCase(dto.getRegRemStatus()))
                .count();
        long pausedCount = remittances.stream()
                .filter(dto -> PAUSED.equalsIgnoreCase(dto.getRegRemStatus()))
                .count();
        long stoppedCount = remittances.stream()
                .filter(dto -> STOPPED.equalsIgnoreCase(dto.getRegRemStatus()))
                .count();

        BigDecimal totalAmount = remittances.stream()
                .map(RegularRemittanceResponseDto::getSendAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new RemittanceSummaryView(remittances.size(), totalAmount, activeCount, pausedCount, stoppedCount);
    }
}
