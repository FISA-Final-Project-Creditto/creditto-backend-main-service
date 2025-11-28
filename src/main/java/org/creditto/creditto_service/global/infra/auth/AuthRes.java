package org.creditto.creditto_service.global.infra.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthRes<T>(
        @JsonProperty("code") Integer code,
        @JsonProperty("message") String message,
        @JsonProperty("data") T data
) {
}
