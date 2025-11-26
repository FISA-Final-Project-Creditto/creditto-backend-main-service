package org.creditto.creditto_service.domain.consent.repository;

import org.creditto.creditto_service.domain.consent.entity.ConsentRecord;
import org.creditto.creditto_service.domain.consent.entity.ConsentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsentRecordRepository extends JpaRepository<ConsentRecord, Long> {

    /**
     * 특정 클라이언트가 주어진 동의 코드(code)의 최신 버전에 동의했는지 여부를 확인
     *
     * @param userId 조회할 userId
     * @param code     확인하려는 동의서의 코드
     * @return 최신 버전에 동의한 경우 true, 그렇지 않으면 false
     */
    @Query("""
            SELECT count(r) > 0
            FROM ConsentRecord r
            WHERE r.userId = :userId
            AND r.consentStatus = :status
            AND r.consentDefinition.consentCode = :code
            AND r.consentRecVer = (
                SELECT MAX(d.consentDefVer)
                FROM ConsentDefinition d
                WHERE d.consentCode = :code
            )
    """)
    boolean existsLatestAgreement(
            @Param("userId") Long userId,
            @Param("code") String code,
            @Param("status") ConsentStatus status
    );


    /**
     * 특정 클라이언트의 모든 동의 기록을 조회
     *
     * @param userId 조회할 userId
     * @return 해당 클라이언트의 모든 ConsentRecord 목록
     */
    @Query("SELECT r FROM ConsentRecord r JOIN FETCH r.consentDefinition WHERE r.userId = :userId")
    List<ConsentRecord> findAllByUserIdWithDefinition(@Param("userId") Long userId);

    Optional<ConsentRecord> findFirstByUserIdAndConsentDefinitionIdAndConsentStatusOrderByConsentDateDesc(
            Long userId, Long definitionId, ConsentStatus status
    );
}
