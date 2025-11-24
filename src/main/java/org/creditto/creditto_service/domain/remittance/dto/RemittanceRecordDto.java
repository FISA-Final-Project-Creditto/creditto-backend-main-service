package org.creditto.creditto_service.domain.remittance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemittanceRecordDto {
    private Long remittanceId;
    private Long regRemId;

    private Long recipientId;
    private String recipientName;
    private String recipientBankName;

    private Long accountId;
    private Long recurId;

    private String sendCurrency;
    private BigDecimal sendAmount;
    private String receiveCurrency;
    private BigDecimal receiveAmount;


    private LocalDate startDate;

    private String remittanceStatus;

}
