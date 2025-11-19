package org.creditto.creditto_service.domain.consent.repository;

import org.creditto.creditto_service.domain.consent.entity.ConsentDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsentDefinitionRepository extends JpaRepository<ConsentDefinition, Long> {


    // Code 기준 동의서 최신 버전 1개 조회 (JPA Derived Query)
    Optional<ConsentDefinition> findTopByConsentCodeOrderByConsentDefVerDesc(String consentCode);

    @Query("""
            SELECT d
            FROM ConsentDefinition d
            WHERE (d.consentCode, d.consentDefVer) IN (
                SELECT d2.consentCode, MAX(d2.consentDefVer)
                FROM ConsentDefinition d2
                GROUP BY d2.consentCode
            )
            """)
    List<ConsentDefinition> findLatestForAllCodes();
}
