package org.creditto.creditto_service.global.infra.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import feign.micrometer.MicrometerObservationCapability;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthFeignConfig {

    @Bean
    public ErrorDecoder authFeignErrorDecoder(ObjectMapper objectMapper) {
        return new AuthFeignErrorDecoder(objectMapper);
    }
}
