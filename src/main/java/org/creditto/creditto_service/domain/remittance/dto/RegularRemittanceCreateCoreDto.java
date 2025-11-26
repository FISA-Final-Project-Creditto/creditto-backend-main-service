package org.creditto.creditto_service.domain.remittance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegularRemittanceCreateCoreDto {
    private String accountNo;

    private String sendCurrency;
    private String receiveCurrency;
    private BigDecimal sendAmount;

    private String regRemType;      // 매월/매주
    private Integer scheduledDate;  // 매월 - 날짜
    private String scheduledDay;    // 매주 - 요일

    private String recipientName;
    private String recipientPhoneCc;
    private String recipientPhoneNo;
    private String recipientAddress;
    private String recipientCountry;
    private String recipientBankName;
    private String recipientBankCode;
    private String recipientAccountNo;

    public static RegularRemittanceCreateCoreDto from(RegularRemittanceCreateDto dto) {
        return RegularRemittanceCreateCoreDto.builder()
                .accountNo(dto.getAccountNo())
                .sendCurrency(dto.getSendCurrency())
                .receiveCurrency(dto.getReceiveCurrency())
                .sendAmount(dto.getSendAmount())
                .regRemType(dto.getRegRemType())
                .scheduledDate(dto.getScheduledDate())
                .scheduledDay(dto.getScheduledDay())
                .recipientName(dto.getRecipientName())
                .recipientPhoneCc(dto.getRecipientPhoneCc())
                .recipientPhoneNo(dto.getRecipientPhoneNo())
                .recipientAddress(dto.getRecipientAddress())
                .recipientCountry(dto.getRecipientCountry())
                .recipientBankName(dto.getRecipientBankName())
                .recipientBankCode(dto.getRecipientBankCode())
                .recipientAccountNo(dto.getRecipientAccountNo())
                .build();
    }
}
