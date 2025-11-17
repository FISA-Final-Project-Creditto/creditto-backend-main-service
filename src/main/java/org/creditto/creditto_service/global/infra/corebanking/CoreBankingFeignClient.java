package org.creditto.creditto_service.global.infra.corebanking;

import org.creditto.creditto_service.global.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "core-banking",
        url = "${CORE_BANKING_SERVER_URL}",
        configuration = FeignConfig.class
)
public interface CoreBankingFeignClient {
}
