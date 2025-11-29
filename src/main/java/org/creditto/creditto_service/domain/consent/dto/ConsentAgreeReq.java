package org.creditto.creditto_service.domain.consent.dto;

/**
 * 동의 요청 DTO
 *
 * @param definitionId 동의서 정의 ID
 * @param ipAddress    사용자 IP 주소
 */
public record ConsentAgreeReq(
        Long definitionId,
        String ipAddress
) {
}
