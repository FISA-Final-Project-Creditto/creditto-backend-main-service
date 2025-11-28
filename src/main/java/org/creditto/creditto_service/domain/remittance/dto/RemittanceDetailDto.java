package org.creditto.creditto_service.domain.remittance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class RemittanceDetailDto {
    private String accountNo;
    private BigDecimal sendAmount;
    private String regRemType;      // 매월, 매주
    private Integer scheduledDate;
    private DayOfWeek scheduledDay;
    private String startedAt;

    // 인증 서버에서
//    private String clientName;
//    private String clientCountry;
    private String sendCurrency;

    private String recipientCountry;
    private String recipientBankName;
    private String recipientAccountNo;
    private String receiveCurrency;
    private String recipientName;
    private String recipientPhoneCc;
    private String recipientPhoneNo;

    private String regRemStatus;
}
