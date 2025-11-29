package org.creditto.creditto_service.domain.consent.dto;

/**
 * 동의 요청 DTO
 *
 * @param userId     클라이언트 ID
 * @param definitionId 동의서 정의 ID
 * @param ipAddress    사용자 IP 주소
 */
public record ConsentAgreeReq(
        String userId,
        Long definitionId,
        String ipAddress
) {
}
