package org.creditto.creditto_service.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.admin.dto.RemittanceDashboardView;
import org.creditto.creditto_service.domain.admin.service.AdminRemittanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/remittances")
@RequiredArgsConstructor
public class AdminRemittanceController {

    private final AdminRemittanceService adminRemittanceService;

    @GetMapping
    public String viewRemittances(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long regRemId,
            @RequestParam(required = false) Long remittanceId,
            Model model
    ) {
        RemittanceDashboardView dashboard = adminRemittanceService.buildDashboard(userId, regRemId, remittanceId);
        model.addAttribute("dashboard", dashboard);
        return "admin/remittances";
    }
}
