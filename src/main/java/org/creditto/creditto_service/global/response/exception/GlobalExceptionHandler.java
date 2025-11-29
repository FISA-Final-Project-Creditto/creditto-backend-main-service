package org.creditto.creditto_service.global.response.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.creditto.creditto_service.global.response.ApiResponseUtil;
import org.creditto.creditto_service.global.response.BaseResponse;
import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 예측 가능하고, 유형이 정해진 비즈니스 예외를 처리하기 위해 사용
     * 명확하게 정의된 에러 상황에서 발생하며, 클라이언트는 이 응답의 {@code ErrorCode}를 받음
     *
     * @param e 처리할 {@code CustomBaseException} 객체
     * @return ErrorCode의 HTTP 상태 코드와 메시지를 포함하는 {@link ResponseEntity}
     */
    @ExceptionHandler(CustomBaseException.class)
    public ResponseEntity<BaseResponse<Void>> handleCustomBaseException(final CustomBaseException e) {
        logWarn(e);
        return ApiResponseUtil.failure(e.getErrorCode()); // 409/메시지 등 ErrorCode 기반으로 응답
    }

    /**
     * 예측하기 어렵거나, 일회성으로 발생하는 예외를 처리하기 위해 사용
     * 개발 과정에서 특정 비즈니스 로직 검증 실패 시, 매번 {@code ErrorCode}를 정의하지 않고
     * {@code new CustomException("에러 메시지")}와 같이 간단하게 예외를 발생시키고 싶을 때 유용
     * 이 경우, HTTP 상태 코드는 항상 400 Bad Request로 고정되며, 상세 내용은 동적으로 전달된 메시지에 의해 결정
     *
     * @param e 처리할 {@code CustomException} 객체
     * @return 400 Bad Request 상태 코드와 예외 메시지를 포함하는 {@link ResponseEntity}
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Void>> handleCustomException(final CustomException e) {
        logWarn(e);
        return ApiResponseUtil.failure(ErrorBaseCode.BAD_REQUEST, e.getMessage());
    }

    /**
     * 400 - HttpMessageNotReadableException
     * 예외 내용 : JSON 혹은 Request Body 파싱 실패
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        logWarn(e);
        return ApiResponseUtil.failure(ErrorBaseCode.NOT_READABLE);
    }

    /**
     * CoreBanking OpenFeign 호출 실패 시 반환
     * CoreBanking 서버의 응답을 그대로 전달하여 클라이언트가 동일한 응답 포맷을 받을 수 있도록 처리
     */
    @ExceptionHandler(CoreBankingFeignException.class)
    public ResponseEntity<BaseResponse<Void>> handleCoreBankingFeignException(final CoreBankingFeignException e) {
        logWarn(e);
        int code = e.getCode() != null ? e.getCode() : ErrorBaseCode.INTERNAL_SERVER_ERROR.getCode();
        String message = StringUtils.hasText(e.getMessage())
                ? e.getMessage()
                : ErrorBaseCode.INTERNAL_SERVER_ERROR.getMessage();
        return ResponseEntity.status(e.getHttpStatus())
                .body(BaseResponse.of(code, message));
    }

    /**
     * CreditRating OpenFeign 호출 실패 시 반환
     * CreditRating 서버의 응답을 그대로 전달하여 클라이언트가 동일한 응답 포맷을 받을 수 있도록 처리
     */
    @ExceptionHandler(CreditRatingFeignException.class)
    public ResponseEntity<BaseResponse<Void>> handleCreditRatingFeignException(final CreditRatingFeignException e) {
        logWarn(e);
        int code = e.getCode() != null ? e.getCode() : ErrorBaseCode.INTERNAL_SERVER_ERROR.getCode();
        String message = StringUtils.hasText(e.getMessage())
                ? e.getMessage()
                : ErrorBaseCode.INTERNAL_SERVER_ERROR.getMessage();
        return ResponseEntity.status(e.getHttpStatus())
                .body(BaseResponse.of(code, message));
    }

    /**
     * 400 - MethodArgumentNotValidException
     * 예외내용 : Argument 유효성 오류
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handlerMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        logWarn(e);
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        if (errorMessage.isBlank()) {
            errorMessage = ErrorBaseCode.INVALID_REQUEST_BODY.getMessage();
        }
        return ApiResponseUtil.failure(ErrorBaseCode.INVALID_REQUEST_BODY, errorMessage);
    }

    /**
     * 400 - UnsatisfiedServletRequestParameterException
     * 예외 내용 : 필수 파라미터 누락
     */
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<BaseResponse<Void>> handleUnsatisfiedServletRequestParameterException(final UnsatisfiedServletRequestParameterException e) {
        logWarn(e);
        return ApiResponseUtil.failure(ErrorBaseCode.MISSING_PARAM, e.getMessage());
    }

    /**
     * 400 - IllegalArgumentException
     * 예외 내용 : 잘못된 인자값 전달로 인한 오류
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Void>> handleIllegalArgumentException(final IllegalArgumentException e) {
        logWarn(e);
        return ApiResponseUtil.failure(ErrorBaseCode.BAD_REQUEST, e.getMessage());
    }

    /**
     * 404 - EntityNotFoundException
     * 예외 내용 : 리소스에 대한 엔티티를 찾을 수 없는 오류
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleEntityNotFoundException(final EntityNotFoundException e) {
        logWarn(e);
        return ApiResponseUtil.failure(ErrorBaseCode.NOT_FOUND_ENTITY, e.getMessage());
    }

    /**
     * 404 - NoHandlerFoundException
     * 예외 내용 : 잘못된 api로 요청했을 때 발생
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleNoHandlerFoundException(final NoHandlerFoundException e) {
        logWarn(e);
        return ApiResponseUtil.failure(ErrorBaseCode.NOT_FOUND_API);
    }

    /**
     * 404 - NoResourceFoundException
     * 예외 내용 : 잘못된 엔드포인트로 요청했을 때 발생
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleNoResourceFoundException(final NoResourceFoundException e) {
        logWarn(e);
        return ApiResponseUtil.failure(ErrorBaseCode.NOT_FOUND_API);
    }

    /**
     * 405 - HttpRequestMethodNotSupportedException
     * 예외 내용 : 잘못된 HTTP METHOD로 요청했을 때 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        logWarn(e);
        return ApiResponseUtil.failure(ErrorBaseCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 400, 500 - TransactionSystemException, ConstraintViolationException
     * 예외 내용 : 트랜잭션 관련 에러
     */
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<BaseResponse<Void>> handleTx(TransactionSystemException e) {
        logWarn(e);
        Throwable root = NestedExceptionUtils.getMostSpecificCause(e);
        if (root instanceof ConstraintViolationException cve) {
            String errorMessage = cve.getConstraintViolations().stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining("\n"));
            return ApiResponseUtil.failure(ErrorBaseCode.BAD_REQUEST, errorMessage);
        }
        return ApiResponseUtil.failure(ErrorBaseCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 - ServerError
     * 예외 내용 : 서버 내부 오류
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleServerException(final Exception e) {
        logError(e);
        return ApiResponseUtil.failure(ErrorBaseCode.INTERNAL_SERVER_ERROR);
    }


    private void logWarn(Exception e) {
        log.warn("[{}]: message={}", e.getClass().getSimpleName(), e.getMessage(), e);
    }


    private void logError(Exception e) {
        log.error("[{}]: message={}", e.getClass().getSimpleName(), e.getMessage(), e);
    }
}
