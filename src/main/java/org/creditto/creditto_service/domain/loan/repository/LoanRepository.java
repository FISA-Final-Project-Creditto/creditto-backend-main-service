package org.creditto.creditto_service.domain.loan.repository;

import org.creditto.creditto_service.domain.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
