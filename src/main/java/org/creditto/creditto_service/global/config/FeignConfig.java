package org.creditto.creditto_service.global.config;

import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableFeignClients(basePackages = "org.creditto.creditto_service.global.infra")
public class FeignConfig {

    @Bean
    Retryer.Default retryer() {
        // 최초 요청 포함 최대 3번 시도
        return new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(1L), 3);
    }
}
