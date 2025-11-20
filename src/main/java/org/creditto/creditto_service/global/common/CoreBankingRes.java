package org.creditto.creditto_service.global.common;

public record CoreBankingRes<T>(
        Integer code,
        String message,
        T data
) {
}
