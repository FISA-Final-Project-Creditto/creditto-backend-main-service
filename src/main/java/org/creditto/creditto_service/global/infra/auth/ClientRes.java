package org.creditto.creditto_service.global.infra.auth;

public record ClientRes(
        String name,
        String birthDate,
        String countryCode,
        String phoneNo,
        String address
) {
}
