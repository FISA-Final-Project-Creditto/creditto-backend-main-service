package org.creditto.creditto_service.domain.loan.entity;

import jakarta.persistence.*;
import lombok.*;
import org.creditto.creditto_service.global.common.BaseEntity;

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
    private Double baseRate;

    // 최대 한도
    private BigInteger maxLimitAmount;

    // 상환 방법
    private RepaymentType repayMentType;

    public static Loan create(String title, String description, Double baseRate, BigInteger maxLimitAmount, RepaymentType repayMentType) {
        return Loan.builder()
                .title(title)
                .description(description)
                .baseRate(baseRate)
                .maxLimitAmount(maxLimitAmount)
                .repayMentType(repayMentType)
                .build();
    }
}
