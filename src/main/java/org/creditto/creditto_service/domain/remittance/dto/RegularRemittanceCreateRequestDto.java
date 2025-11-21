package org.creditto.creditto_service.domain.remittance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegularRemittanceCreateRequestDto {
    private Long accountId;
    private Long recipientId;
    private String sendCurrency;
    private String receivedCurrency;
    private BigDecimal sendAmount;
    private String regRemStatus;

    private String regRemType;      // 매월/매주

    private Integer scheduledDate;  // 매월 송금
    private String scheduledDay;    // 매주 송금
}
