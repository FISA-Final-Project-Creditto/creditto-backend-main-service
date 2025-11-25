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
public class RegularRemittanceCreateDto {
    @NotBlank(message = "출금 계좌번호는 필수입니다.")
    private String accountNo;

    @NotBlank
    private String sendCurrency;

    @NotBlank
    private String receiveCurrency;

    @NotNull(message = "송금 금액을 입력해주세요.")
    @Positive(message = "송금 금액은 0보다 커야합니다.")
    private BigDecimal sendAmount;

    @Pattern(regexp = "MONTHLY|WEEKLY", message = "주기는 MONTHLY 또는 WEEKLY여야 합니다.")
    private String regRemType;      // 매월/매주

    private Integer scheduledDate;  // 매월 - 날짜

    private String scheduledDay;    // 매주 - 요일

    // 송금인 이름, 국적, 주소 저장은 인증서버에
    @NotBlank
    private String clientName;

    @NotBlank
    private String clientCountry;

    @NotBlank
    private String clientAddress;

    @NotBlank
    private String recipientName;

    @NotBlank
    private String recipientPhoneCc;

    @NotBlank
    private String recipientPhoneNo;

    @NotBlank
    private String recipientAddress;

    @NotBlank
    private String recipientCountry;

    @NotBlank
    private String recipientBankName;

    @NotBlank
    private String recipientBankCode;

    @NotBlank
    private String recipientAccountNo;
}
