package org.creditto.creditto_service.domain.creditScore.service;

import com.lowagie.text.DocumentException;
import org.creditto.creditto_service.global.infra.auth.AuthFeignClient;
import org.creditto.creditto_service.global.infra.auth.AuthRes;
import org.creditto.creditto_service.global.infra.auth.ClientRes;
import org.creditto.creditto_service.global.infra.creditrating.CreditRatingFeignClient;
import org.creditto.creditto_service.global.infra.creditrating.CreditScoreFeaturesRes;
import org.creditto.creditto_service.global.infra.creditrating.CreditScoreReportRes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class CreditScoreServiceIntegrationTest {

    @Autowired
    private CreditScoreService creditScoreService;

    @MockBean // Spring Context에 있는 실제 Bean 대신 Mock Bean을 사용
    private CreditRatingFeignClient creditRatingFeignClient;

    @MockBean
    private AuthFeignClient authFeignClient;


    @Test
    @DisplayName("실제 HTML 템플릿과 데이터로 PDF 생성 통합 테스트")
    void generateCreditScoreReportPdf_integrationTest() throws IOException, DocumentException {
        // given: 테스트에 사용할 데이터 설정
        Long userId = 1L;

        // Mock 데이터 생성: ClientRes
        ClientRes mockClientRes = new ClientRes(
                "Test User",
                "1990-01-01",
                "KOR",
                "010-1234-5678",
                "Seoul, Korea"
        );
        AuthRes<ClientRes> mockAuthRes = new AuthRes<>(200, "조회성공", mockClientRes);

        // Mock 데이터 생성: CreditScoreReportRes
        CreditScoreFeaturesRes mockFeaturesRes = new CreditScoreFeaturesRes(
                new BigDecimal("3500000"), // incomeAvg6m
                new BigDecimal("0.1"),   // incomeVolatility6m
                new BigDecimal("1200000"), // spendingAvg6m
                0.355,                   // savingRate6m
                new BigDecimal("540000"), // minBalance3m
                2.5,                     // liquidityMonths3m
                12,                      // remittanceCount6m
                new BigDecimal("500000"), // remittanceAmountAvg6m
                new BigDecimal("50000"),  // remittanceAmountStd6m
                0.15,                    // remittanceIncomeRatio
                0.0,                     // remittanceFailureRate6m
                0.95,                    // remittanceCycleStability
                0.2,                     // dtiLoanRatio
                0.9,                     // loanOverdueScore
                0,                       // recentOverdueFlag
                0.2,                     // cardUtilization3m
                0.05,                    // cardCashAdvanceRatio
                0                        // riskEventCount
        );
        CreditScoreReportRes mockReportRes = new CreditScoreReportRes(850, mockFeaturesRes);

        // Feign Client가 호출될 때 위에서 만든 가짜 데이터를 리턴하도록 설정
        when(creditRatingFeignClient.getCreditScoreReport(userId)).thenReturn(mockReportRes);
        when(authFeignClient.getUserInformation(anyLong())).thenReturn(mockAuthRes);

        // when: 실제 서비스 로직 호출 (한국어)
        byte[] pdfBytesKo = creditScoreService.generateCreditScoreReportPdf(userId, "ko");

        // then: 한국어 결과 검증
        assertNotNull(pdfBytesKo);
        assertTrue(pdfBytesKo.length > 0, "생성된 한국어 PDF 파일이 비어있습니다.");

        // 생성된 한국어 PDF를 임시 파일로 저장
        java.nio.file.Path tempFileKo = java.nio.file.Files.createTempFile("creditto-credit-report-test-ko-", ".pdf");
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(tempFileKo.toFile())) {
            fos.write(pdfBytesKo);
        }
        System.out.println("Test PDF (Korean) generated at: " + tempFileKo.toAbsolutePath());

        // when: 실제 서비스 로직 호출 (영어)
        byte[] pdfBytesEn = creditScoreService.generateCreditScoreReportPdf(userId, "en");

        // then: 영어 결과 검증
        assertNotNull(pdfBytesEn);
        assertTrue(pdfBytesEn.length > 0, "생성된 영어 PDF 파일이 비어있습니다.");

        // 생성된 영어 PDF를 임시 파일로 저장
        java.nio.file.Path tempFileEn = java.nio.file.Files.createTempFile("creditto-credit-report-test-en-", ".pdf");
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(tempFileEn.toFile())) {
            fos.write(pdfBytesEn);
        }
        System.out.println("Test PDF (English) generated at: " + tempFileEn.toAbsolutePath());


    }
}
