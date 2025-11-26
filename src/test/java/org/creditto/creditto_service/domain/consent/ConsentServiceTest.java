package org.creditto.creditto_service.domain.consent;

import org.creditto.creditto_service.domain.consent.dto.ConsentDefinitionRes;
import org.creditto.creditto_service.domain.consent.dto.ConsentRecordRes;
import org.creditto.creditto_service.domain.consent.entity.ConsentCategory;
import org.creditto.creditto_service.domain.consent.entity.ConsentDefinition;
import org.creditto.creditto_service.domain.consent.entity.ConsentRecord;
import org.creditto.creditto_service.domain.consent.entity.ConsentStatus;
import org.creditto.creditto_service.domain.consent.repository.ConsentDefinitionRepository;
import org.creditto.creditto_service.domain.consent.repository.ConsentRecordRepository;
import org.creditto.creditto_service.domain.consent.service.ConsentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ConsentServiceTest {

    @Autowired
    private ConsentService consentService;

    @Autowired
    private ConsentDefinitionRepository definitionRepository;

    @Autowired
    private ConsentRecordRepository recordRepository;

    @AfterEach
    void tearDown() {
        recordRepository.deleteAllInBatch();
        definitionRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("동의하기 - 성공")
    void agree_Success() {
        // Given
        ConsentDefinition definition = ConsentDefinition.of("CODE1", "Title", "Desc", ConsentCategory.MARKETING, 2, LocalDateTime.now(), null);
        definitionRepository.save(definition);

        // When
        Long userId = 1L;
        ConsentRecordRes result = consentService.agree(userId, "CODE1");

        // Then
        List<ConsentRecord> records = recordRepository.findAll();
        assertThat(records).hasSize(1);
        ConsentRecord savedRecord = records.get(0);

        assertThat(result.consentCode()).isEqualTo("CODE1");
        assertThat(result.consentStatus()).isEqualTo(ConsentStatus.AGREE);
        assertThat(result.consentRecVer()).isEqualTo(2);
        assertThat(result.userId()).isEqualTo(userId);

        assertThat(savedRecord.getConsentDefinition().getConsentCode()).isEqualTo("CODE1");
        assertThat(savedRecord.getConsentStatus()).isEqualTo(ConsentStatus.AGREE);
    }

    @Test
    @DisplayName("철회하기 - 성공")
    void withdraw_Success() {
        // Given
        Long userId = 1L;
        ConsentDefinition definition = definitionRepository.save(ConsentDefinition.of("CODE1", "Title", "Desc", ConsentCategory.MARKETING, 1, LocalDateTime.now(), null));
        ConsentRecord consentRecord = recordRepository.save(ConsentRecord.of(definition, userId));

        // When
        consentService.withdraw(userId, definition.getConsentCode());

        // Then
        ConsentRecord withdrawnRecord = recordRepository.findById(consentRecord.getId()).get();
        assertThat(withdrawnRecord.getConsentStatus()).isEqualTo(ConsentStatus.WITHDRAW);
        assertThat(withdrawnRecord.getWithdrawalDate()).isNotNull();
    }

    @Test
    @DisplayName("모든 최신 동의서 조회")
    void getLatestConsentDefinitions_Success() {
        // Given
        definitionRepository.save(ConsentDefinition.of("CODE1", "Title1", "Desc1", ConsentCategory.MARKETING, 1, LocalDateTime.now(), null));
        definitionRepository.save(ConsentDefinition.of("CODE1", "Title1 v2", "Desc1 v2", ConsentCategory.MARKETING, 2, LocalDateTime.now(), null));
        definitionRepository.save(ConsentDefinition.of("CODE2", "Title2", "Desc2", ConsentCategory.SERVICE, 1, LocalDateTime.now(), null));

        // When
        List<ConsentDefinitionRes> results = consentService.getLatestConsentDefinitions();

        // Then
        assertThat(results).hasSize(2);
        assertThat(results).extracting(ConsentDefinitionRes::consentCode).containsExactlyInAnyOrder("CODE1", "CODE2");
        // CODE1의 최신 버전인 2가 조회되어야 함
        ConsentDefinitionRes code1Result = results.stream().filter(r -> r.consentCode().equals("CODE1")).findFirst().get();
        assertThat(code1Result.consentDefVer()).isEqualTo(2);
    }

    @Test
    @DisplayName("사용자 동의 내역 조회")
    void getConsentRecord_Success() {
        // Given
        Long userId = 1L;
        ConsentDefinition def1 = definitionRepository.save(ConsentDefinition.of("CODE1", "Title1", "Desc1", ConsentCategory.MARKETING, 1, LocalDateTime.now(), null));
        ConsentDefinition def2 = definitionRepository.save(ConsentDefinition.of("CODE2", "Title2", "Desc2", ConsentCategory.SERVICE, 1, LocalDateTime.now(), null));
        recordRepository.save(ConsentRecord.of(def1, userId));
        ConsentRecord recordToWithdraw = ConsentRecord.of(def2, userId);
        recordToWithdraw.withdraw();
        recordRepository.save(recordToWithdraw);

        // When
        List<ConsentRecordRes> results = consentService.getConsentRecord(userId);

        // Then
        assertThat(results).hasSize(2);
        assertThat(results).extracting(ConsentRecordRes::userId).containsOnly(userId);
    }

    @Test
    @DisplayName("최신 버전 동의 여부 확인 - 동의함")
    void checkAgreement_Agreed() {
        // Given
        Long userId = 1L;
        String code = "CODE1";
        ConsentDefinition definition = definitionRepository.save(ConsentDefinition.of(code, "Title", "Desc", ConsentCategory.MARKETING, 2, LocalDateTime.now(), null));
        recordRepository.save(ConsentRecord.of(definition, userId));

        // When
        boolean hasAgreed = consentService.checkAgreement(userId, code);

        // Then
        assertThat(hasAgreed).isTrue();
    }

    @Test
    @DisplayName("최신 버전 동의 여부 확인 - 이전 버전에 동의함")
    void checkAgreement_AgreedToOlderVersion() {
        // Given
        Long userId = 1L;
        String code = "CODE1";
        definitionRepository.save(ConsentDefinition.of(code, "Title", "Desc", ConsentCategory.MARKETING, 2, LocalDateTime.now(), null));
        ConsentDefinition oldDefinition = definitionRepository.save(ConsentDefinition.of(code, "Title", "Desc", ConsentCategory.MARKETING, 1, LocalDateTime.now(), null));
        recordRepository.save(ConsentRecord.of(oldDefinition, userId));

        // When
        boolean hasAgreed = consentService.checkAgreement(userId, code);

        // Then
        assertThat(hasAgreed).isFalse();
    }

    @Test
    @DisplayName("최신 버전 동의 여부 확인 - 철회함")
    void checkAgreement_Withdrawn() {
        // Given
        Long userId = 1L;
        String code = "CODE1";
        ConsentDefinition definition = definitionRepository.save(ConsentDefinition.of(code, "Title", "Desc", ConsentCategory.MARKETING, 2, LocalDateTime.now(), null));
        ConsentRecord recordToWithdraw = ConsentRecord.of(definition, userId);
        recordToWithdraw.withdraw();
        recordRepository.save(recordToWithdraw);

        // When
        boolean hasAgreed = consentService.checkAgreement(userId, code);

        // Then
        assertThat(hasAgreed).isFalse();
    }

    @Test
    @DisplayName("최신 버전 동의 여부 확인 - 기록 없음")
    void checkAgreement_NoRecord() {
        // Given
        Long userId = 1L;
        String code = "CODE1";
        definitionRepository.save(ConsentDefinition.of(code, "Title", "Desc", ConsentCategory.MARKETING, 1, LocalDateTime.now(), null));

        // When
        boolean hasAgreed = consentService.checkAgreement(userId, code);

        // Then
        assertThat(hasAgreed).isFalse();
    }
}
