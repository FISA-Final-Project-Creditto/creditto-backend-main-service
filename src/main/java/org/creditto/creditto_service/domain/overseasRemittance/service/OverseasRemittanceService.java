package org.creditto.creditto_service.domain.overseasRemittance.service;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.overseasRemittance.dto.OverseasRemittanceReq;
import org.creditto.creditto_service.global.common.CoreBankingRes;
import org.creditto.creditto_service.global.infra.corebanking.AccountRes;
import org.creditto.creditto_service.global.infra.corebanking.CoreBankingFeignClient;
import org.creditto.creditto_service.global.infra.corebanking.OverseasRemittanceRes;
import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.creditto.creditto_service.global.response.exception.CustomBaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OverseasRemittanceService {

    private final CoreBankingFeignClient coreBankingFeignClient;

    @Transactional
    public OverseasRemittanceRes processRemittanceOnce(String userId, Long accountId, OverseasRemittanceReq request) {
        AccountRes account = coreBankingFeignClient.getAccountByAccountId(accountId).data();

        if (!account.clientId().equals(userId)) {
            // TODO: 접근 권한 예외로 변경
            throw new CustomBaseException(ErrorBaseCode.NOT_FOUND_ACCOUNT);
        }

        if (!account.accountNo().equals(request.accountNo())) {
            // TODO: 잘못된 요청 관련 예외로 변경
            throw new CustomBaseException(ErrorBaseCode.NOT_FOUND_ACCOUNT);
        }

        return coreBankingFeignClient.processRemittanceOnce(userId, request).data();
    }
}
