package org.creditto.creditto_service.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.remittance.dto.RegularRemittanceResponseDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceDetailDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceHistoryDetailDto;
import org.creditto.creditto_service.domain.remittance.dto.RemittanceHistoryDto;
import org.creditto.creditto_service.domain.remittance.service.RemittanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/remittances")
@RequiredArgsConstructor
public class AdminRemittanceController {

    private final RemittanceService remittanceService;

    @GetMapping
    public String viewRemittances(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long regRemId,
            @RequestParam(required = false) Long remittanceId,
            Model model
    ) {
        populateRemittancePage(userId, regRemId, remittanceId, model);
        return "admin/remittances";
    }

    private void populateRemittancePage(Long userId, Long regRemId, Long remittanceId, Model model) {
        model.addAttribute("searchUserId", userId);
        model.addAttribute("historyRemittanceId", remittanceId);

        if (userId == null) {
            return;
        }

        List<RegularRemittanceResponseDto> remittances = remittanceService.getScheduledRemittanceList(userId);
        model.addAttribute("remittances", remittances);
        addRemittanceStats(remittances, model);

        if (regRemId == null) {
            return;
        }

        model.addAttribute("selectedRemittanceId", regRemId);
        RemittanceDetailDto detail = remittanceService.getScheduledRemittanceDetail(userId, regRemId);
        model.addAttribute("remittanceDetail", detail);

        List<RemittanceHistoryDto> history = remittanceService.getScheduledRemittanceHistory(userId, regRemId);
        model.addAttribute("historyRecords", history);

        if (remittanceId == null) {
            return;
        }

        RemittanceHistoryDetailDto historyDetail =
                remittanceService.getScheduledRemittanceHistoryDetail(userId, regRemId, remittanceId);
        model.addAttribute("historyDetail", historyDetail);
    }

    private void addRemittanceStats(List<RegularRemittanceResponseDto> remittances, Model model) {
        if (remittances == null || remittances.isEmpty()) {
            return;
        }

        long activeCount = remittances.stream()
                .filter(dto -> "ACTIVE".equalsIgnoreCase(dto.getRegRemStatus()))
                .count();
        long pausedCount = remittances.stream()
                .filter(dto -> "PAUSED".equalsIgnoreCase(dto.getRegRemStatus()))
                .count();
        long stoppedCount = remittances.stream()
                .filter(dto -> "STOPPED".equalsIgnoreCase(dto.getRegRemStatus()))
                .count();

        model.addAttribute("totalRemittanceCount", remittances.size());
        model.addAttribute("totalSendAmount", remittances.stream()
                .map(RegularRemittanceResponseDto::getSendAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("pausedCount", pausedCount);
        model.addAttribute("stoppedCount", stoppedCount);
    }
}
