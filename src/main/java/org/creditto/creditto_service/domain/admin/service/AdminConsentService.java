package org.creditto.creditto_service.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.admin.dto.ConsentDefinitionForm;
import org.creditto.creditto_service.domain.admin.dto.ConsentDefinitionView;
import org.creditto.creditto_service.domain.consent.entity.ConsentDefinition;
import org.creditto.creditto_service.domain.consent.repository.ConsentDefinitionRepository;
import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.creditto.creditto_service.global.response.exception.CustomBaseException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminConsentService {

    private final ConsentDefinitionRepository consentDefinitionRepository;

    public List<ConsentDefinitionView> getAllDefinitions() {
        return consentDefinitionRepository.findAll(Sort.by(Sort.Direction.ASC, "consentCategory", "consentDefVer"))
                .stream()
                .map(ConsentDefinitionView::from)
                .toList();
    }

    public ConsentDefinitionForm getDefinitionForm(Long id) {
        ConsentDefinition definition = getDefinition(id);
        return ConsentDefinitionForm.from(definition);
    }

    @Transactional
    public void createDefinition(ConsentDefinitionForm form) {
        consentDefinitionRepository.save(form.toEntity());
    }

    @Transactional
    public void updateDefinition(Long id, ConsentDefinitionForm form) {
        ConsentDefinition definition = getDefinition(id);
        definition.update(form);
    }

    @Transactional
    public void deleteDefinition(Long id) {
        ConsentDefinition definition = getDefinition(id);
        consentDefinitionRepository.delete(definition);
    }

    private ConsentDefinition getDefinition(Long id) {
        return consentDefinitionRepository.findById(id)
                .orElseThrow(() -> new CustomBaseException(ErrorBaseCode.NOT_FOUND_DEFINITION));
    }
}
