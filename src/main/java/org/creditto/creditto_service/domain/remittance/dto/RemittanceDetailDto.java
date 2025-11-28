package org.creditto.creditto_service.domain.remittance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.creditto.creditto_service.global.infra.auth.ClientRes;

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
    private LocalDate startedAt;

    private String clientName;
    private String clientCountry;
    private String sendCurrency;

    private String recipientCountry;
    private String recipientBankName;
    private String recipientAccountNo;
    private String receiveCurrency;
    private String recipientName;
    private String recipientPhoneCc;
    private String recipientPhoneNo;

    private String regRemStatus;

    public static RemittanceDetailDto of(RemittanceDetailDto remittanceDetailDto, ClientRes clientRes) {
        return RemittanceDetailDto.builder()
                .accountNo(remittanceDetailDto.getAccountNo())
                .sendAmount(remittanceDetailDto.getSendAmount())
                .regRemType(remittanceDetailDto.getRegRemType())
                .scheduledDate(remittanceDetailDto.getScheduledDate())
                .scheduledDay(remittanceDetailDto.getScheduledDay())
                .startedAt(remittanceDetailDto.getStartedAt())
                .clientName(clientRes.name())
                .clientCountry(clientRes.countryCode())
                .sendCurrency(remittanceDetailDto.getSendCurrency())
                .recipientCountry(remittanceDetailDto.getRecipientCountry())
                .recipientBankName(remittanceDetailDto.getRecipientBankName())
                .recipientAccountNo(remittanceDetailDto.getRecipientAccountNo())
                .receiveCurrency(remittanceDetailDto.getReceiveCurrency())
                .recipientName(remittanceDetailDto.getRecipientName())
                .recipientPhoneCc(remittanceDetailDto.getRecipientPhoneCc())
                .recipientPhoneNo(remittanceDetailDto.getRecipientPhoneNo())
                .regRemStatus(remittanceDetailDto.getRegRemStatus())
                .build();
    }
}
