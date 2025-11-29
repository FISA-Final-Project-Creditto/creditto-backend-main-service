package org.creditto.creditto_service.domain.consent.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.consent.dto.ConsentAgreeReq;
import org.creditto.creditto_service.domain.consent.dto.ConsentDefinitionRes;
import org.creditto.creditto_service.domain.consent.dto.ConsentRecordRes;
import org.creditto.creditto_service.domain.consent.entity.ConsentDefinition;
import org.creditto.creditto_service.domain.consent.entity.ConsentRecord;
import org.creditto.creditto_service.domain.consent.entity.ConsentStatus;
import org.creditto.creditto_service.domain.consent.repository.ConsentDefinitionRepository;
import org.creditto.creditto_service.domain.consent.repository.ConsentRecordRepository;
import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.creditto.creditto_service.global.response.exception.CustomBaseException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 동의서 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class ConsentService {

    private final ConsentDefinitionRepository definitionRepository;
    private final ConsentRecordRepository recordRepository;

    /**
     * 사용자의 동의를 기록
     * @param userId 동의한 userId
     * @return ConsentRecordRes 생성된 동의 기록 정보
     */
    @Transactional
    public ConsentRecordRes agree(Long userId, org.creditto.creditto_service.domain.consent.dto.ConsentAgreeReq req) {

        ConsentDefinition definition = definitionRepository.findById(req.definitionId())
                .orElseThrow(() -> new CustomBaseException(ErrorBaseCode.NOT_FOUND_DEFINITION));

        ConsentRecord consentRecord = recordRepository
                .findFirstByUserIdAndConsentDefinitionIdAndConsentStatusOrderByConsentDateDesc(
                        userId, definition.getId(), ConsentStatus.AGREE
                )
                .orElseGet(() -> {
                    ConsentRecord newRecord = ConsentRecord.of(definition, userId, req.ipAddress());
                    return recordRepository.save(newRecord);
                });

        return ConsentRecordRes.from(consentRecord);
    }

    /**
     * 사용자의 동의를 철회
     * @param userId 철회할 userId
     * @param consentCode 철회할 동의서 코드
     * @throws CustomBaseException 철회할 동의 기록을 찾을 수 없을 때 발생
     */
    @Transactional
    public void withdraw(Long userId, String consentCode) {
        ConsentRecord findRecord = recordRepository.findFirstByUserIdAndCodeAndStatus(
                userId, consentCode, ConsentStatus.AGREE
        ).orElseThrow(
                () -> new CustomBaseException(ErrorBaseCode.NOT_FOUND_RECORD)
        );

        findRecord.withdraw();
    }

    /**
     * 같은 코드의 모든 동의서 최신 버전을 조회
     *
     * @return 최신 버전의 모든 동의서 목록
     */
    public List<ConsentDefinitionRes> getLatestConsentDefinitions() {
        return definitionRepository.findLatestForAllCodes().stream()
                .map(ConsentDefinitionRes::from)
                .toList();
    }

    /**
     * 특정 ID의 동의서 정의를 조회
     *
     * @param definitionId 동의서 ID
     * @return 해당 ID의 동의서 정의 정보
     * @throws CustomBaseException 해당 ID의 동의서 정의를 찾을 수 없을 때 발생
     */
    public ConsentDefinitionRes getConsentDefinition(Long definitionId) {
        return definitionRepository.findById(definitionId)
                .map(ConsentDefinitionRes::from)
                .orElseThrow(() -> new CustomBaseException(ErrorBaseCode.NOT_FOUND_DEFINITION));
    }

    /**
     * 특정 사용자의 전체 동의 기록을 조회
     * @param userId 조회할 userId
     * @return 동의한 동의서 list
     */
    public List<ConsentRecordRes> getConsentRecord(Long userId) {
        return recordRepository.findAllByUserIdWithDefinition(userId).stream()
                .map(ConsentRecordRes::from)
                .toList();
    }

    /**
     * 특정 사용자가 특정 동의 코드의 최신 버전에 동의했는지 확인
     * @param userId 조회할 userId
     * @param code 조회할 동의서 code
     * @return 동의 여부
     */
    public boolean checkAgreement(Long userId, String code) {
        return recordRepository.existsLatestAgreement(userId, code, ConsentStatus.AGREE);
    }
}
