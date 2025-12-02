package org.creditto.creditto_service.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.global.config.MonitoringProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/monitoring")
@RequiredArgsConstructor
public class AdminMonitoringController {

    private final MonitoringProperties monitoringProperties;

    @GetMapping
    public String monitoring(Model model) {
        model.addAttribute("kibanaUrl", monitoringProperties.kibanaUrl());
        return "admin/monitoring";
    }
}
