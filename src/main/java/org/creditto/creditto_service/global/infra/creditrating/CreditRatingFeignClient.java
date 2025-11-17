package org.creditto.creditto_service.global.infra.creditrating;

import org.creditto.creditto_service.global.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "credit-rating",
        url = "${CREDIT_RATING_SERVER_URL}",
        configuration = FeignConfig.class
)
public interface CreditRatingFeignClient {
}
