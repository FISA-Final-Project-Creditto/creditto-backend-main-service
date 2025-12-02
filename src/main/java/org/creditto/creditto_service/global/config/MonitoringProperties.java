package org.creditto.creditto_service.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin.monitoring")
public record MonitoringProperties(
        String kibanaUrl
) {
}
