package org.creditto.creditto_service.domain.admin.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.admin.service.AdminAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AdminLoginController {

    public static final String ADMIN_SESSION_KEY = "ADMIN_AUTHENTICATED";
    private final AdminAuthService adminAuthService;

    @GetMapping("/admin/login")
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/admin/login/basic")
    public String loginWithForm(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        if (adminAuthService.authenticate(username, password)) {
            session.setAttribute(ADMIN_SESSION_KEY, Boolean.TRUE);
            return "redirect:/admin/dashboard";
        }
        session.invalidate();
        redirectAttributes.addFlashAttribute("loginError", true);
        return "redirect:/admin/login";
    }
}
