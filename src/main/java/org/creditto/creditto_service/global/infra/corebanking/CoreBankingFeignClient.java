package org.creditto.creditto_service.global.infra.corebanking;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "core-banking",
        url = "${CORE_BANKING_SERVER_URL}"
)
public interface CoreBankingFeignClient {
}
