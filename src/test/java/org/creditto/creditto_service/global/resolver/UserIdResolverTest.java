package org.creditto.creditto_service.global.resolver;

import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.creditto.creditto_service.global.response.exception.CustomBaseException;
import org.creditto.creditto_service.global.security.TokenAuthentication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

class UserIdResolverTest {

    private final UserIdResolver resolver = new UserIdResolver();

    @AfterEach
    void clear() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Long 타입의 userId가 담긴 인증객체가 SecurityContext에 존재할 경우 userId를 정상적으로 Resolve")
    void resolveArgument_returnsPrincipal_whenAuthenticated() {
        // Given
        Authentication auth = TokenAuthentication.create(
                null,
                null,
                1L,
                "exUser"
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        // When
        Object result = resolver.resolveArgument(null, null, null, null);

        // Then
        assertEquals(1L, result);
    }

    @Test
    @DisplayName("SecurityContext 내에 Authentication이 존재하지 않으면 CustomBaseException을 반환한다")
    void resolveArgument_throws_whenNoAuthentication() {
        // Given
        SecurityContextHolder.clearContext();

        // When & Then
        CustomBaseException ex = assertThrows(CustomBaseException.class,
                () -> resolver.resolveArgument(null, null, null, null));
        assertEquals(ErrorBaseCode.NO_AUTHENTICATION, ex.getErrorCode());
    }

    @Test
    @DisplayName("Authetication이 인증되지 않았다면 CustomBaseException을 반환한다")
    void resolveArgument_throws_whenNotAuthenticated() {
        // Given
        Authentication auth = TokenAuthentication.create(
                null,
                null,
                1L,
                "exUser"
        );
        auth.setAuthenticated(false);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // When & Then
        CustomBaseException ex = assertThrows(CustomBaseException.class,
                () -> resolver.resolveArgument(null, null, null, null));
        assertEquals(ErrorBaseCode.NO_AUTHENTICATION, ex.getErrorCode());
    }
}
