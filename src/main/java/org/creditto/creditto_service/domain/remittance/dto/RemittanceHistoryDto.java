package org.creditto.creditto_service.domain.remittance.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class RemittanceHistoryDto {
    private BigDecimal sendAmount;
    private BigDecimal exchangeRate;

    private LocalDate createdDate;
}
