package org.creditto.creditto_service.domain.admin.dto;

import org.creditto.creditto_service.domain.consent.entity.ConsentCategory;
import org.creditto.creditto_service.domain.consent.entity.ConsentDefinition;

import java.time.LocalDateTime;

public record ConsentDefinitionView(
        Long id,
        String consentTitle,
        ConsentCategory consentCategory,
        Integer consentDefVer,
        LocalDateTime validFrom,
        LocalDateTime validTo
) {
    public static ConsentDefinitionView from(ConsentDefinition definition) {
        return new ConsentDefinitionView(
                definition.getId(),
                definition.getConsentTitle(),
                definition.getConsentCategory(),
                definition.getConsentDefVer(),
                definition.getValidFrom(),
                definition.getValidTo()
        );
    }
}
