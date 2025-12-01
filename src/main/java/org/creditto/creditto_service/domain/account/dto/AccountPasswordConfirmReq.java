package org.creditto.creditto_service.domain.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AccountPasswordConfirmReq(
        @JsonProperty("password")
        @NotBlank
        @Pattern(regexp = "^\\d{4}$", message = "비밀번호는 4자리 숫자여야 합니다.")
        String password
) {
}
