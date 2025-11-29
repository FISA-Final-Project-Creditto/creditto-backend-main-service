package org.creditto.creditto_service.global.response.exception;

import lombok.Getter;

@Getter
public class AuthFeignException extends RuntimeException {

    private final int httpStatus;
    private final Integer code;

    public AuthFeignException(int httpStatus, Integer code, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
    }
}
