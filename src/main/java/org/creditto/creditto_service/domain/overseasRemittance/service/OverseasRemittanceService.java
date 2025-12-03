package org.creditto.creditto_service.domain.overseasRemittance.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.overseasRemittance.dto.OverseasRemittanceReq;
import org.creditto.creditto_service.global.infra.corebanking.AccountRes;
import org.creditto.creditto_service.global.infra.corebanking.CoreBankingFeignClient;
import org.creditto.creditto_service.global.infra.corebanking.OverseasRemittanceRes;
import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.creditto.creditto_service.global.response.exception.CustomBaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OverseasRemittanceService {

    private final CoreBankingFeignClient coreBankingFeignClient;

    @Transactional
    public OverseasRemittanceRes processRemittanceOnce(Long userId, OverseasRemittanceReq request) {
        AccountRes account = coreBankingFeignClient.getAccountByAccountNo(request.accountNo()).data();

        if (account == null) {
            throw new CustomBaseException(ErrorBaseCode.NOT_FOUND_ACCOUNT);
        }

        if (!account.userId().equals(userId)) {
            throw new CustomBaseException(ErrorBaseCode.FORBIDDEN);
        }

        return coreBankingFeignClient.processRemittanceOnce(userId, request).data();
    }

    public List<OverseasRemittanceRes> getRemittanceList(Long userId) {
        return coreBankingFeignClient.getRemittanceList(userId).data();
    }
}
