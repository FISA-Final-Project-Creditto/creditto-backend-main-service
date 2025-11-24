package org.creditto.creditto_service.global.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Map;

/**
 * TokenAuthentication
 * - Resource Server에서 검증 완료된 Jwt를 바탕으로 생성되는 커스텀 인증 객체
 * - 토큰 내 핵심 식별 정보(userId, userName)를 분리 보관하여 도메인 로직에서 쉽게 사용
 */
public class TokenAuthentication extends AbstractAuthenticationToken {

    private final Jwt jwt;
    private final Long userId;
    private final String userName;

    private TokenAuthentication(
            Jwt jwt,
            Collection<? extends GrantedAuthority> authorities,
            Long userId,
            String userName
    ) {
        super(authorities);
        this.jwt = jwt;
        this.userId = userId;
        this.userName = userName;
        super.setAuthenticated(true);
    }

    public static TokenAuthentication create(
            Jwt jwt,
            Collection<? extends GrantedAuthority> authorities,
            Long userId,
            String userName
    ) {
        return new TokenAuthentication(jwt, authorities, userId, userName);
    }

    // 자격 증명은 노출하지 않음 (Bearer 토큰은 요청 헤더로만 전달됨)
    @Override
    public Object getCredentials() {
        return null;
    }

    // 주체(Principal)는 서비스 정책에 맞게 userId 또는 userName 중 하나를 반환
    // 여기서는 식별자(userId)를 우선 사용
    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public String getName() {
        return userName != null ? userName : String.valueOf(userId);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Jwt getJwt() {
        return jwt;
    }

    public Map<String, Object> getClaims() {
        return jwt.getClaims();
    }

    public <T> T getClaim(String claimName) {
        return jwt.getClaim(claimName);
    }

    public String getUserName() {
        return userName;
    }
}
