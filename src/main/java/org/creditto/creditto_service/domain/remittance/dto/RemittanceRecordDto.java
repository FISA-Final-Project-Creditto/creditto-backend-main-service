package org.creditto.creditto_service.domain.remittance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.creditto.creditto_service.domain.remittance.enums.CurrencyCode;
import org.creditto.creditto_service.domain.remittance.enums.RemittanceStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemittanceRecordDto {
    private Long remittanceId;
    private Long recipientId;
    private Long accountId;
    private Long recurId;
    private Long exchangeId;
    private Long feeRecordId;

    private CurrencyCode sendCurrency;
    private CurrencyCode receiveCurrency;

    private BigDecimal sendAmount;
    private BigDecimal receiveAmount;

    private LocalDate startDate;

    private RemittanceStatus remittanceStatus;

}
