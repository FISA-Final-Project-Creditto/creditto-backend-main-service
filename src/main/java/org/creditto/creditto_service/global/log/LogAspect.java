package org.creditto.creditto_service.global.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.creditto.creditto_service.global.response.BaseResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final ObjectMapper objectMapper;

    @Pointcut("execution(* org.creditto.creditto_service.domain..controller.*.*(..))")
    private void onRequest() {}

    @Pointcut("execution(* org.creditto.creditto_service.domain..service.*.*(..))")
    private void onService() {}

    @Pointcut("execution(* org.creditto.creditto_service.global.infra..*FeignClient.*(..))")
    private void onFeignClient() {}

    @Around("onRequest()")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String className = joinPoint.getTarget().getClass().getSimpleName();

        log.info("[{}] Request IP : {} | Request URI : {} | Request Method : {}",
                className,
                request.getRemoteAddr(),
                request.getRequestURI(),
                request.getMethod()
        );

        Object[] args = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> !(arg instanceof HttpServletRequest))
                .filter(arg -> !(arg instanceof jakarta.servlet.http.HttpServletResponse))
                .filter(arg -> !(arg instanceof org.springframework.validation.BindingResult))
                .toArray();

        String argsAsString = toJsonString(args);

        // Request Body (DTO) 로깅
        if (args.length > 0) {
            log.info("[{}] {} {} - RequestBody: {}",
                    className,
                    request.getMethod(),
                    request.getRequestURI(),
                    argsAsString
            );
        }

        // 실제 비즈니스 실행
        Object result = joinPoint.proceed();

        // Response Body 로깅
        Object responseBody = result;
        Object dataForLog;

        if (result instanceof ResponseEntity<?> responseEntity) {
            responseBody = responseEntity.getBody();
        }

        if (responseBody instanceof BaseResponse<?> baseResponse) {
            dataForLog = baseResponse.getData();
        } else {
            dataForLog = responseBody;
        }

        String dataAsString = toJsonString(dataForLog);

        log.info("[{}] {} {} - ResponseData: {}",
                className,
                request.getMethod(),
                request.getRequestURI(),
                dataAsString
        );

        return result;
    }

    @Before("onService()")
    public void beforeServiceLog(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("[{}] {}() called", className, methodName);

        if (args.length > 0) {
            log.debug("[{}] Parameters: {}", className, Arrays.toString(args));
        }
    }

    @Around("onFeignClient()")
    public Object logFeignRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodSignature = joinPoint.getSignature().toShortString();
        String argsJson = toJsonString(joinPoint.getArgs());

        log.info("[Feign] Request -> {} | args={}", methodSignature, argsJson);

        try {
            Object response = joinPoint.proceed();
            log.info("[Feign] Response <- {} | body={}", methodSignature, toJsonString(response));
            return response;
        } catch (Exception ex) {
            log.warn("[Feign] Error <- {} | message={}", methodSignature, ex.getMessage());
            throw ex;
        }
    }

    private String toJsonString(Object value) {
        if (value == null) {
            return "null";
        }

        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return String.valueOf(value);
        }
    }
}
