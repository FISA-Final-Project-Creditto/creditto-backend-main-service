package org.creditto.creditto_service.domain.overseasRemittance.dto;

public record RecipientInfoReq(
        String name,
        String accountNo,
        String bankName,
        String bankCode,
        String phoneCc,
        String phoneNo,
        String country
) {
}
