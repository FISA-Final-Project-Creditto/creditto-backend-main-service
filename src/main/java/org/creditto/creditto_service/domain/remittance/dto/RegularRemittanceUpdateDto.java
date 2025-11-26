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
public class RegularRemittanceUpdateDto {
    private String accountNo;
    private BigDecimal sendAmount;
    private String regRemStatus;
    private Integer scheduledDate;  // 매월일 경우 수정할 날짜
    private String scheduledDay;    // 매주일 경우 수정할 요일
}
