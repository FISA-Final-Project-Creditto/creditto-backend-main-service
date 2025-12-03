package org.creditto.creditto_service.domain.overseasRemittance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.creditto.creditto_service.global.common.CurrencyCode;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OverseasRemittanceReq(
        @NotBlank(message = "출금 계좌번호는 필수입니다.")
        String accountNo,

        @NotBlank(message = "계좌 비밀번호는 필수입니다.")
        @Pattern(regexp = "^\\d{4}$", message = "비밀번호는 4자리 숫자여야 합니다.")
        String password,

        @Valid
        @NotNull(message = "수취인 정보는 필수입니다.")
        RecipientInfoReq recipientInfo,

        Long recurId,

        LocalDate startDate,

        @NotNull(message = "송금 통화코드는 필수입니다.")
        CurrencyCode sendCurrency,

        @NotNull(message = "송금액은 필수입니다.")
        @Positive(message = "송금액은 0보다 커야 합니다.")
        BigDecimal targetAmount
) {
}
