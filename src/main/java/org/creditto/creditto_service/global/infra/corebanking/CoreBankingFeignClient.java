package org.creditto.creditto_service.global.infra.corebanking;

import org.creditto.creditto_service.domain.account.dto.CreateAccountReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "core-banking",
        url = "${CORE_BANKING_SERVER_URL}"
)
public interface CoreBankingFeignClient {
    @PostMapping(value = "/api/core/account/{externalUserId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    CreateAccountRes createAccountAPI(@PathVariable String externalUserId, @RequestBody CreateAccountReq request);
}
