package org.creditto.creditto_service;

import org.creditto.creditto_service.global.config.AdminAuthProperties;
import org.creditto.creditto_service.global.config.MonitoringProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({MonitoringProperties.class, AdminAuthProperties.class})
public class CredittoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CredittoServiceApplication.class, args);
	}

}
