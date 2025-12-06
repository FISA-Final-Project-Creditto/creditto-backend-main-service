package org.creditto.creditto_service.domain.consent.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.consent.dto.ConsentDefinitionRes;
import org.creditto.creditto_service.domain.consent.repository.ConsentDefinitionRepository;
import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.creditto.creditto_service.global.response.exception.CustomBaseException;
import org.springframework.stereotype.Service;


/**
 * 동의서 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class ConsentService {

    private final ConsentDefinitionRepository definitionRepository;


    private


    /**
     * 특정 ID의 동의서를 조회
     *
     * @param definitionId 동의서 ID
     * @return 해당 ID의 동의서 정의 정보
     * @throws CustomBaseException 해당 ID의 동의서 정의를 찾을 수 없을 때 발생
     */
    public ConsentDefinitionRes getConsentDefinition(Long definitionId) {
        definitionRepository.findById(definitionId)
                .map(ConsentDefinitionRes::from)
                .orElseThrow(() -> new CustomBaseException(ErrorBaseCode.NOT_FOUND_DEFINITION));
    }
}
