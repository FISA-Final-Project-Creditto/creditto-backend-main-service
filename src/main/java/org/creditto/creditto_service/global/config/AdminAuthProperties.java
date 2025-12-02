package org.creditto.creditto_service.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin.auth")
public record AdminAuthProperties(
        String username,
        String password
) {
}
