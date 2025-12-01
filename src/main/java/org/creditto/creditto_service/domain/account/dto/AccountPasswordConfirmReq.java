package org.creditto.creditto_service.domain.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountPasswordConfirmReq(
        @JsonProperty("password")
        String password
) {
}
