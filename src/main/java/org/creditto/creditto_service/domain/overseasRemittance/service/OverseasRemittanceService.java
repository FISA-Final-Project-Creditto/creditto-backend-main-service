package org.creditto.creditto_service.domain.overseasRemittance.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.overseasRemittance.dto.OverseasRemittanceReq;
import org.creditto.creditto_service.global.infra.corebanking.CoreBankingFeignClient;
import org.creditto.creditto_service.global.infra.corebanking.OverseasRemittanceRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OverseasRemittanceService {

    private final CoreBankingFeignClient coreBankingFeignClient;

    public OverseasRemittanceRes processRemittanceOnce(String userId, OverseasRemittanceReq request) {
        return coreBankingFeignClient.processRemittanceOnce(userId, request);
    }
}
