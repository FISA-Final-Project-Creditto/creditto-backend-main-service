package org.creditto.creditto_service.global.infra.creditrating;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record CreditScoreFeaturesRes(
    @JsonProperty("income_avg_6m")
    BigDecimal incomeAvg6m,

    @JsonProperty("income_volatility_6m")
    BigDecimal incomeVolatility6m,

    @JsonProperty("spending_avg_6m")
    BigDecimal spendingAvg6m,

    @JsonProperty("saving_rate_6m")
    Double savingRate6m,

    @JsonProperty("min_balance_3m")
    BigDecimal minBalance3m,

    @JsonProperty("liquidity_months_3m")
    Double liquidityMonths3m,

    @JsonProperty("remittance_count_6m")
    Integer remittanceCount6m,

    @JsonProperty("remittance_amount_avg_6m")
    BigDecimal remittanceAmountAvg6m,

    @JsonProperty("remittance_amount_std_6m")
    BigDecimal remittanceAmountStd6m,

    @JsonProperty("remittance_income_ratio")
    Double remittanceIncomeRatio,

    @JsonProperty("remittance_failure_rate_6m")
    Double remittanceFailureRate6m,

    @JsonProperty("remittance_cycle_stability")
    Double remittanceCycleStability,

    @JsonProperty("dti_loan_ratio")
    Double dtiLoanRatio,

    @JsonProperty("loan_overdue_score")
    Double loanOverdueScore,

    @JsonProperty("recent_overdue_flag")
    Integer recentOverdueFlag,

    @JsonProperty("card_utilization_3m")
    Double cardUtilization3m,

    @JsonProperty("card_cash_advance_ratio")
    Double cardCashAdvanceRatio,

    @JsonProperty("risk_event_count")
    Integer riskEventCount
) {}
