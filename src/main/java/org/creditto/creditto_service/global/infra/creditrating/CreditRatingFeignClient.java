package org.creditto.creditto_service.global.infra.creditrating;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "credit-rating",
        url = "${CREDIT_RATING_SERVER_URL}"
)
public interface CreditRatingFeignClient {
}
