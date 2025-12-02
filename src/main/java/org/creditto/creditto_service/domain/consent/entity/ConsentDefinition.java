package org.creditto.creditto_service.domain.consent.entity;

import jakarta.persistence.*;
import lombok.*;
import org.creditto.creditto_service.domain.admin.dto.ConsentDefinitionForm;
import org.creditto.creditto_service.global.common.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsentDefinition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "definition_id")
    private Long id;

    @Lob
    private String consentTitle;

    @Lob
    private String consentDesc;

    @Enumerated(EnumType.STRING)
    private ConsentCategory consentCategory;

    private Integer consentDefVer; // 동의서 버전

    private LocalDateTime validFrom; // 유효 시작일

    private LocalDateTime validTo; // 유효 종료일

    public static ConsentDefinition of(String consentTitle, String consentDesc, ConsentCategory consentCategory, Integer consentDefVer, LocalDateTime validFrom, LocalDateTime validTo) {
        return ConsentDefinition.builder()
                .consentTitle(consentTitle)
                .consentDesc(consentDesc)
                .consentCategory(consentCategory)
                .consentDefVer(consentDefVer)
                .validFrom(validFrom)
                .validTo(validTo)
                .build();
    }

    public void update(ConsentDefinitionForm form) {
        this.consentTitle = form.getConsentTitle();
        this.consentDesc = form.getConsentDesc();
        this.consentCategory = form.getConsentCategory();
        this.consentDefVer = form.getConsentDefVer();
        this.validFrom = form.getValidFrom();
        this.validTo = form.getValidTo();
    }
}
