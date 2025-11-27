package org.creditto.creditto_service.domain.remittance.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.DayOfWeek;

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

    @Nullable private Integer scheduledDate;  // 매월 - 날짜

    @Nullable private DayOfWeek scheduledDay;    // 매주 - 요일

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

    @AssertTrue(message = "MONTHLY는 날짜(Date)가, WEEKLY는 요일(Day)이 필수이며 교차 입력은 불가합니다.")
    public boolean isScheduleValid() {
        // 타입이 없으면 패스
        if (regRemType == null) return true;

        if ("MONTHLY".equals(regRemType)) {
            // MONTHLY일 때: Date는 있어야 하고, Day는 없어야 함
            return scheduledDate != null && scheduledDay == null;
        }

        if ("WEEKLY".equals(regRemType)) {
            // WEEKLY일 때: Day는 있어야 하고, Date는 없어야 함
            return scheduledDay != null && scheduledDate == null;
        }

        return false; // 그 외 이상한 타입
    }
}
