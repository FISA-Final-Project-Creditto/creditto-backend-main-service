import org.creditto.creditto_service.domain.consent.dto.ConsentAgreeReq;
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
        ConsentAgreeReq req = new ConsentAgreeReq(
                "test-client",
                definition.getId(),
                "127.0.0.1"
        );

        // When
        Long userId = 1L;
        ConsentRecordRes result = consentService.agree(userId, req);

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
