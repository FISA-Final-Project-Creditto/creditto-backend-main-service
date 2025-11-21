package org.creditto.creditto_service.global.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CoreBankingRes<T>(
        @JsonProperty("code") Integer code,
        @JsonProperty("message") String message,
        @JsonProperty("data") T data
) {
}
