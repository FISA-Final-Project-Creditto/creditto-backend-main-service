package org.creditto.creditto_service.global.infra.corebanking;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreBankingFeignConfig {

    @Bean
    public ErrorDecoder coreBankingFeignErrorDecoder(ObjectMapper objectMapper) {
        return new CoreBankingFeignErrorDecoder(objectMapper);
    }
}