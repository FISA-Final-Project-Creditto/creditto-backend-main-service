package org.creditto.creditto_service.domain.consent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.creditto.creditto_service.domain.consent.dto.ConsentDefinitionRes;
import org.creditto.creditto_service.domain.consent.repository.ConsentDefinitionRepository;
import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.creditto.creditto_service.global.response.exception.CustomBaseException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * 동의서 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConsentService {

    private final ConsentDefinitionRepository definitionRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 특정 ID의 동의서를 조회
     *
     * @param definitionId 동의서 ID
     * @return 해당 ID의 동의서 정의 정보
     * @throws CustomBaseException 해당 ID의 동의서 정의를 찾을 수 없을 때 발생
     */
    public ConsentDefinitionRes getConsentDefinition(Long definitionId) {
        String key = "consent::" + definitionId;

        // Redis 캐시 조회
        try {
            Object cachedData = redisTemplate.opsForValue().get(key);
            if (cachedData != null) {
                // ObjectMapper를 사용하여 캐시된 객체를 DTO로 변환
                return objectMapper.convertValue(cachedData, ConsentDefinitionRes.class);
            }
        } catch (Exception e) {
            log.warn("Failed to read or convert cache data for key: {}. Proceeding to DB.", key, e);
            // 캐시 읽기/변환 실패 시 키를 삭제
            redisTemplate.delete(key);
        }

        // redis에 캐싱된 값이 없으면 DB에서 조회
        ConsentDefinitionRes consentDefinitionRes = definitionRepository.findById(definitionId)
                .map(ConsentDefinitionRes::from)
                .orElseThrow(() -> new CustomBaseException(ErrorBaseCode.NOT_FOUND_DEFINITION));

        // DB에서 조회한 DTO 객체를 Redis에 저장
        try {
            redisTemplate.opsForValue().set(key, consentDefinitionRes);
        } catch (Exception e) {
            log.error("Failed to cache data for key: {}", key, e);
        }

        return consentDefinitionRes;
    }
}
