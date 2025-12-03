package org.creditto.creditto_service.global.infra.corebanking;

import java.math.BigDecimal;

public record PreferentialRateRes(
        double preferentialRate,
        BigDecimal appliedRate
) {
}
