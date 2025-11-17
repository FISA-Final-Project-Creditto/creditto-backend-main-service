package org.creditto.creditto_service.global.security;

import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.creditto.creditto_service.global.response.exception.JwtTokenException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.ArrayList;
import java.util.Collection;

import static org.creditto.creditto_service.global.common.Constants.EXTERNAL_USER_ID;
import static org.creditto.creditto_service.global.common.Constants.USER_NAME;

/**
 * CustomAuthenticationConverter
 * - Spring Security Resource Server가 검증한 Jwt를 애플리케이션의 TokenAuthentication으로 변환
 * - 토큰의 externalUserId, userName을 추출하여 인증 객체에 담아 컨트롤러/서비스에서 쉽게 사용
 */
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    // 기본 스코프(scp/scope) → SCOPE_xxx 권한 변환기
    private final JwtGrantedAuthoritiesConverter defaultAuthorities = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // 기본 권한 매핑
        Collection<GrantedAuthority> authorities = new ArrayList<>(defaultAuthorities.convert(jwt));

        // 토큰 내 정보 추출
        String externalUserId = jwt.getClaimAsString(EXTERNAL_USER_ID);
        String userName = jwt.getClaimAsString(USER_NAME);

        // 예외 처리
        validExternalUserIdAndUserName(externalUserId, userName);

        // TokenAuthentication 반환
        return TokenAuthentication.create(jwt, authorities, externalUserId, userName);
    }

    private static void validExternalUserIdAndUserName(String externalUserId, String userName) {
        if (externalUserId == null || externalUserId.isBlank() || userName == null || userName.isBlank()) {
            throw new JwtTokenException(ErrorBaseCode.INVALID_JWT_TOKEN);
        }
    }
}
