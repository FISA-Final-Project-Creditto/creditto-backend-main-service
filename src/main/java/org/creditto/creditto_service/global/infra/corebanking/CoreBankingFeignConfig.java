package org.creditto.creditto_service.global.infra.corebanking;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.codec.ErrorDecoder;
import feign.micrometer.MicrometerObservationCapability;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CoreBankingFeignConfig {

    private final ObservationRegistry observationRegistry;

    @Value("${CORE_BANKING_SERVER_URL}")
    private String coreBankingUrl;

    @Bean
    public ErrorDecoder coreBankingFeignErrorDecoder(ObjectMapper objectMapper) {
        return new CoreBankingFeignErrorDecoder(objectMapper);
    }

    @Bean
    public CoreBankingFeignClient coreBankingFeignClient(ErrorDecoder coreBankingFeignErrorDecoder) {
        return Feign.builder()
                .errorDecoder(coreBankingFeignErrorDecoder)
                .addCapability(new MicrometerObservationCapability(observationRegistry))
                .target(CoreBankingFeignClient.class, coreBankingUrl);
    }
}