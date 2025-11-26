package org.creditto.creditto_service.domain.consent.repository;

import org.creditto.creditto_service.domain.consent.entity.ConsentRecord;
import org.creditto.creditto_service.domain.consent.entity.ConsentStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsentRecordRepository extends JpaRepository<ConsentRecord, Long> {
    /**
     * 특정 클라이언트와 동의서 정의 ID에 대해 가장 최근의 'AGREE' 상태인 동의 기록을 조회
     * N+1 문제를 방지하기 위해 ConsentDefinition 엔티티를 페치 조인
     *
     * @param userId 조회할 userId
     * @param definitionId 동의서 정의의 ID
     * @param consentStatus 확인할 상태
     * @param pageable 결과를 제한하고 정렬하기 위한 페이징 정보 (최신 기록 1개만 가져오기 위해 사용)
     * @return 조회된 ConsentRecord 목록
     */
    @Query("""
            SELECT r FROM ConsentRecord r
            JOIN FETCH r.consentDefinition
            WHERE r.userId = :userId
            AND r.consentDefinition.id = :definitionId
            AND r.consentStatus = :status
            ORDER BY r.consentDate DESC
    """)
    List<ConsentRecord> findLatestByClientAndDefinitionWithDefinition(
            @Param("userId") Long userId,
            @Param("definitionId") Long definitionId,
            @Param("status") ConsentStatus consentStatus,
            Pageable pageable
    );

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
    List<ConsentRecord> findAllByClientIdWithDefinition(@Param("userId") Long userId);
}
