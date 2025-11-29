package org.creditto.creditto_service.global.infra.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "auth",
        url = "${AUTH_SERVER_URL}",
        configuration = AuthFeignConfig.class
)
public interface AuthFeignClient {

    @GetMapping(value = "/api/user/{userId}")
    AuthRes<ClientRes> getUserInformation(@PathVariable Long userId);
}
