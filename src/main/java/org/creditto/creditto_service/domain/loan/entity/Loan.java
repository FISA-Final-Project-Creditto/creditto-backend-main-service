package org.creditto.creditto_service.domain.loan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.creditto.creditto_service.global.common.BaseEntity;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Loan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    // 기본 금리
    private BigDecimal baseRate;

    // 최대 한도
    private BigInteger maxLimitAmount;

    // 상환 방법
    @Enumerated(EnumType.STRING)
    private RepaymentType repaymentType;

    public static Loan create(String title, String description, BigDecimal baseRate, BigInteger maxLimitAmount, RepaymentType repaymentType) {
        return Loan.builder()
                .title(title)
                .description(description)
                .baseRate(baseRate)
                .maxLimitAmount(maxLimitAmount)
                .repaymentType(repaymentType)
                .build();
    }
}
