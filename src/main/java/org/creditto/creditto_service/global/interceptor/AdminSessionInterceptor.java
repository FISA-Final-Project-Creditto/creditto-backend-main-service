package org.creditto.creditto_service.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.creditto.creditto_service.domain.admin.controller.AdminLoginController;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object flag = session.getAttribute(AdminLoginController.ADMIN_SESSION_KEY);
            if (Boolean.TRUE.equals(flag)) {
                return true;
            }
        }
        response.sendRedirect("/admin/login");
        return false;
    }
}
