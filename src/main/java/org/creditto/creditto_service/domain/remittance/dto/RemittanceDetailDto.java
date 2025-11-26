package org.creditto.creditto_service.domain.remittance.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class RemittanceDetailDto {
    private String accountNo;
    private BigDecimal totalFee;
    private BigDecimal sendAmount;
    private String recipientBankName;
    private String recipientAccountNo;
    private String remittanceStatus;
}
