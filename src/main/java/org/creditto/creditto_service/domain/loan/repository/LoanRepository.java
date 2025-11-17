package org.creditto.creditto_service.domain.loan.repository;

import org.creditto.creditto_service.domain.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> getLoanById(Long id);
}
