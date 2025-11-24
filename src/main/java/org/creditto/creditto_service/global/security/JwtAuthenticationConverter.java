package org.creditto.creditto_service.global.security;

import org.creditto.creditto_service.global.response.exception.JwtTokenException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.ArrayList;
import java.util.Collection;

import static org.creditto.creditto_service.global.common.Constants.USER_ID;
import static org.creditto.creditto_service.global.common.Constants.USER_NAME;
import static org.creditto.creditto_service.global.response.error.ErrorBaseCode.INVALID_JWT_TOKEN;

/**
 * CustomAuthenticationConverter
 * - Spring Security Resource Server가 검증한 Jwt를 애플리케이션의 TokenAuthentication으로 변환
 * - 토큰의 userId, userName을 추출하여 인증 객체에 담아 컨트롤러/서비스에서 쉽게 사용
 */
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    // 기본 스코프(scp/scope) → SCOPE_xxx 권한 변환기
    private final JwtGrantedAuthoritiesConverter defaultAuthorities = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // 기본 권한 매핑
        Collection<GrantedAuthority> authorities = new ArrayList<>(defaultAuthorities.convert(jwt));

        // 토큰 내 정보 추출
        Long userId = extractUserId(jwt);
        String userName = jwt.getClaimAsString(USER_NAME);

        validUserName(userName);

        // TokenAuthentication 반환
        return TokenAuthentication.create(jwt, authorities, userId, userName);
    }

    private Long extractUserId(Jwt jwt) {
        Object claim = jwt.getClaim(USER_ID);
        if (claim == null) {
            throw new JwtTokenException(INVALID_JWT_TOKEN);
        }

        if (claim instanceof Number number) {
            return number.longValue();
        }
        if (claim instanceof String string) {
            return Long.parseLong(string);
        }
        throw new JwtTokenException(INVALID_JWT_TOKEN);
    }

    private void validUserName(String userName) {
        if (userName == null)
            throw new JwtTokenException(INVALID_JWT_TOKEN);
    }
}
