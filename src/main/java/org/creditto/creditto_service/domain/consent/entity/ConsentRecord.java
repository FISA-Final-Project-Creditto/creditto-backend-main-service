package org.creditto.creditto_service.domain.consent.entity;

import jakarta.persistence.*;
import lombok.*;
import org.creditto.creditto_service.global.common.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsentRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "definition_id")
    private ConsentDefinition consentDefinition;

    @Enumerated(EnumType.STRING)
    private ConsentStatus consentStatus;

    private Integer consentRecVer; // 사용자가 동의한 동의서 버전

    private LocalDateTime consentDate; // 동의 시점

    private LocalDateTime withdrawalDate; // 철회 시점

    private Long userId;

    private String ipAddress;

    public static ConsentRecord of(ConsentDefinition consentDefinition, Long userId, String ipAddress) {
        return ConsentRecord.builder()
                .consentDefinition(consentDefinition)
                .consentStatus(ConsentStatus.AGREE)
                .consentRecVer(consentDefinition.getConsentDefVer())
                .consentDate(LocalDateTime.now())
                .withdrawalDate(null)
                .userId(userId)
                .ipAddress(ipAddress)
                .build();
    }

    // 동의 -> 철회 변경
    public void withdraw() {
        this.consentStatus = ConsentStatus.WITHDRAW;
        this.withdrawalDate = LocalDateTime.now();
    }
}