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
public class RegularRemittanceResponseDto {
    private String accountNo;

    private Long regRemId;

    private String recipientName;
    private String recipientBankName;

    private String receivedCurrency;
    private String regRemStatus;

    private String regRemType;            // 매월/매주
    private Integer scheduledDate;        // 매월 송금
    private String scheduledDay;   // 매주 송금
}
