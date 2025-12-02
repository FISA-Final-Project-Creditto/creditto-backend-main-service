package org.creditto.creditto_service.domain.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.creditto.creditto_service.domain.consent.entity.ConsentCategory;
import org.creditto.creditto_service.domain.consent.entity.ConsentDefinition;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsentDefinitionForm {

    @NotBlank
    private String consentTitle;

    @NotBlank
    private String consentDesc;

    @NotNull
    private ConsentCategory consentCategory;

    @NotNull
    @Min(1)
    private Integer consentDefVer;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime validFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime validTo;

    public static ConsentDefinitionForm from(ConsentDefinition definition) {
        return ConsentDefinitionForm.builder()
                .consentTitle(definition.getConsentTitle())
                .consentDesc(definition.getConsentDesc())
                .consentCategory(definition.getConsentCategory())
                .consentDefVer(definition.getConsentDefVer())
                .validFrom(definition.getValidFrom())
                .validTo(definition.getValidTo())
                .build();
    }

    public ConsentDefinition toEntity() {
        return ConsentDefinition.of(
                consentTitle,
                consentDesc,
                consentCategory,
                consentDefVer,
                validFrom,
                validTo
        );
    }
}
