package org.creditto.creditto_service.domain.consent.repository;

import org.creditto.creditto_service.domain.consent.entity.ConsentRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsentRecordRepository extends JpaRepository<ConsentRecord, Long> {

    @Query("SELECT r FROM ConsentRecord r JOIN FETCH r.consentDefinition WHERE r.clientId = :clientId AND r.consentDefinition.id = :definitionId ORDER BY r.consentDate DESC")
    List<ConsentRecord> findLatestByClientAndDefinitionWithDefinition(@Param("clientId") String clientId, @Param("definitionId") Long definitionId, Pageable pageable);

    @Query("""
            SELECT count(r) > 0
            FROM ConsentRecord r
            WHERE r.clientId = :clientId
            AND r.consentStatus = org.creditto.creditto_service.domain.consent.entity.ConsentStatus.AGREE
            AND r.consentDefinition.consentCode = :code
            AND r.consentRecVer = (
            SELECT MAX(d.consentDefVer)
            FROM ConsentDefinition d
            WHERE d.consentCode = :code
              )
            """)
    boolean existsLatestAgreement(@Param("clientId") String clientId, @Param("code") String code);


    @Query("SELECT r FROM ConsentRecord r JOIN FETCH r.consentDefinition WHERE r.clientId = :clientId")
    List<ConsentRecord> findAllByClientIdWithDefinition(@Param("clientId") String clientId);
}
