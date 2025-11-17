package org.creditto.creditto_service.domain.loan.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.creditto.creditto_service.domain.loan.entity.Loan;

import java.math.BigDecimal;
import java.math.BigInteger;

@Builder(access = AccessLevel.PRIVATE)
public record LoanInfoRes(
        Long loanId,
        String title,
        String description,
        BigDecimal baseRate,
        BigInteger maxLimitAmount,
        String repaymentType
) {
    public static LoanInfoRes create(Loan loan) {
        return LoanInfoRes.builder()
                .loanId(loan.getId())
                .title(loan.getTitle())
                .description(loan.getDescription())
                .baseRate(loan.getBaseRate())
                .maxLimitAmount(loan.getMaxLimitAmount())
                .repaymentType(loan.getRepaymentType().getType())
                .build();
    }
}
