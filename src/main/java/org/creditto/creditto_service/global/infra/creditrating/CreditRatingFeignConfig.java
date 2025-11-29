package org.creditto.creditto_service.global.infra.creditrating;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreditRatingFeignConfig {

    @Bean
    public ErrorDecoder creditRatingFeignErrorDecoder(ObjectMapper objectMapper) {
        return new CreditRatingFeignErrorDecoder(objectMapper);
    }
}
