package org.creditto.creditto_service.global.response.exception;

import lombok.Getter;

@Getter
public class CoreBankingFeignException extends RuntimeException {

    private final int httpStatus;
    private final Integer code;

    public CoreBankingFeignException(int httpStatus, Integer code, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
    }
}
