package org.creditto.creditto_service.domain.consent.repository;

import org.creditto.creditto_service.domain.consent.entity.ConsentDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsentDefinitionRepository extends JpaRepository<ConsentDefinition, Long> {


    /**
     * 주어진 동의에 해당하는 동의서들 중에서 가장 최신 버전의 동의서 정의를 조회
     * JPA의 파생 쿼리 기능을 사용하여 구현
     *
     * @param consentCode 조회하려는 동의서의 코드
     * @return 최신 버전의 ConsentDefinition을 담은 Optional 객체. 해당하는 동의서가 없으면 Optional.empty()를 반환
     */
    Optional<ConsentDefinition> findTopByConsentCodeOrderByConsentDefVerDesc(String consentCode);

    /**
     * 모든 동의 코드에 대해 각각의 최신 버전 동의서 정의를 한 번의 쿼리로 조회
     * 서브쿼리를 사용하여 각 consentCode 그룹 내에서 가장 큰 consentDefVer를 가진 레코드를 찾음
     *
     * @return 각 동의 코드별 최신 버전의 ConsentDefinition 목록
     */
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
