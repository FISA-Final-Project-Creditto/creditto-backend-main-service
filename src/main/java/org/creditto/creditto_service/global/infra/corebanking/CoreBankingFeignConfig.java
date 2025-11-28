package org.creditto.creditto_service.global.infra.corebanking;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import feign.micrometer.MicrometerObservationCapability;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CoreBankingFeignConfig {

    @Bean
    public ErrorDecoder coreBankingFeignErrorDecoder(ObjectMapper objectMapper) {
        return new CoreBankingFeignErrorDecoder(objectMapper);
    }

    @Bean
    MicrometerObservationCapability micrometerObservationCapability(ObservationRegistry registry) {
        return new MicrometerObservationCapability(registry);
    }
}