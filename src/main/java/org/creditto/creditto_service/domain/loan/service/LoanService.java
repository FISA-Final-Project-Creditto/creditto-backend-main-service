package org.creditto.creditto_service.domain.loan.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.loan.dto.LoanInfoRes;
import org.creditto.creditto_service.domain.loan.entity.Loan;
import org.creditto.creditto_service.domain.loan.repository.LoanRepository;
import org.springframework.stereotype.Service;

import static org.creditto.creditto_service.global.response.error.ErrorMessage.RESOURCE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    /**
     * 개별 대출 정보 반환
     * @param loanId 대출 정보 Id
     * @return 대출 정보
     */
    public LoanInfoRes getLoanInfo(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException(RESOURCE_NOT_FOUND));

        return LoanInfoRes.create(loan);
    }
}
