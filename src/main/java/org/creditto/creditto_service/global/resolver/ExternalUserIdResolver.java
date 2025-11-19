package org.creditto.creditto_service.global.resolver;

import lombok.extern.slf4j.Slf4j;
import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.creditto.creditto_service.global.response.exception.CustomBaseException;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
public class ExternalUserIdResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (parameter.getParameterType().equals(String.class) && parameter.hasParameterAnnotation(ExternalUserId.class));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       if (authentication == null || !authentication.isAuthenticated()) {
           throw new CustomBaseException(ErrorBaseCode.NO_AUTHENTICATION);
       }

       return String.valueOf(authentication.getPrincipal());
    }
}
