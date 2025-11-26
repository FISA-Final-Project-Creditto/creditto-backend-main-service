package org.creditto.creditto_service.domain.remittance.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegularRemittanceUpdateDto {
    @NotBlank(message = "계좌번호는 필수입니다.")
    private String accountNo;

    @NotNull(message = "송금 금액은 필수입니다.")
    @Positive(message = "송금 금액은 0보다 커야 합니다.") // 0이나 음수 방지
    private BigDecimal sendAmount;

    @Pattern(regexp = "ACTIVE|PAUSED|STOPPED", message = "상태는 ACTIVE, PAUSED, STOPPED 중 하나여야 합니다.")
    private String regRemStatus;

    @Min(value = 1, message = "날짜는 1일 이상이어야 합니다.")
    @Max(value = 31, message = "날짜는 31일 이하여야 합니다.")
    private Integer scheduledDate;

    @Pattern(regexp = "MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY",
            message = "요일 형식이 올바르지 않습니다.")
    private String scheduledDay;
}
