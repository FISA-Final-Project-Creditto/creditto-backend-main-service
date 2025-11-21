package org.creditto.creditto_service.domain.remittance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.creditto.creditto_service.domain.remittance.enums.RegRemStatus;
import org.creditto.creditto_service.domain.remittance.enums.ScheduledDay;

import java.math.BigDecimal;
import java.time.DayOfWeek;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegularRemittanceDto {
    private Long regRemId;
    private Long accountId;
    private Long recipientId;
    private String sendCurrency;
    private String receivedCurrency;
    private BigDecimal sendAmount;
    private RegRemStatus regRemStatus;

    private String regRemType;      // 매월/매주

    private Integer scheduledDate;  // 매월 송금
    private ScheduledDay scheduledDay;    // 매주 송금
}
