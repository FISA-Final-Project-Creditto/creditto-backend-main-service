package org.creditto.creditto_service.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.global.config.AdminAuthProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AdminAuthProperties adminAuthProperties;

    public boolean authenticate(String username, String password) {
        return adminAuthProperties.username().equals(username) &&
                adminAuthProperties.password().equals(password);
    }
}
