package org.creditto.creditto_service.domain.creditScore.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.creditScore.dto.CreditScorePredictReq;
import org.creditto.creditto_service.domain.creditScore.dto.CreditScoreReq;
import org.creditto.creditto_service.global.infra.auth.AuthFeignClient;
import org.creditto.creditto_service.global.infra.auth.ClientRes;
import org.creditto.creditto_service.global.infra.creditrating.*;
import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.creditto.creditto_service.global.response.exception.CustomBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import feign.FeignException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreditScoreService {

    private static final Logger log = LoggerFactory.getLogger(CreditScoreService.class);

    private static final String MALGUN_GOTHIC_FONT_PATH = "/fonts/malgun.ttf";
    private final CreditRatingFeignClient creditRatingFeignClient;
    private final AuthFeignClient authFeignClient;
    private final TemplateEngine templateEngine;

    public CreditScoreRes calculateCreditScore(CreditScoreReq creditScoreReq) {
        return creditRatingFeignClient.calculateCreditScore(creditScoreReq);
    }

    public CreditScoreRes getCreditScore(Long userId) {
        return creditRatingFeignClient.getCreditScore(userId);
    }

    public CreditScoreHistoryRes getCreditScoreHistory(Long userId) {
        return creditRatingFeignClient.getCreditScoreHistory(userId);
    }

    public CreditScorePredictRes predictCreditScore(CreditScorePredictReq creditScorePredictReq) {
        return creditRatingFeignClient.predictCreditScore(creditScorePredictReq);
    }

    /**
     * 신용점수 리포트 PDF 생성
     * @param userId 사용자 ID
     * @return PDF byte array
     */
    public byte[] generateCreditScoreReportPdf(Long userId, String lang) {
        try {
            String html = buildReportHtml(userId, lang);
            return convertHtmlToPdf(html);
        } catch (CustomBaseException e) { // 하위 메서드에서 발생시킨 CustomBaseException
            log.warn("CustomBaseException propagated during PDF report generation for user {}: {}", userId, e.getMessage(), e);
            throw e;
        } catch (feign.FeignException e) { // Feign 클라이언트 통신 오류 처리
            log.error("Feign client call failed during PDF report generation for user {}: {}", userId, e.getMessage(), e);
            throw new CustomBaseException(ErrorBaseCode.API_CALL_ERROR);
        } catch (Exception e) { //이외의 에러
            log.error("An unexpected error occurred during PDF report generation for user {}: {}", userId, e.getMessage(), e);
            throw new CustomBaseException(ErrorBaseCode.INTERNAL_SERVER_ERROR);
        }
    }

    // HTML 생성 로직 (템플릿 + 데이터 바인딩)
    private String buildReportHtml(Long userId, String lang) {
        CreditScoreReportRes reportData = creditRatingFeignClient.getCreditScoreReport(userId);
        ClientRes clientRes = authFeignClient.getUserInformation(userId).data();

        Context context = new Context();
        context.setVariable("client", clientRes);
        context.setVariable("finalScore", reportData.creditScore());
        context.setVariable("calculatedDate", LocalDate.now());
        context.setVariable("visa", Map.of(
                "type", "E-9",
                "expireDate", "2026-10-15",
                "remainingDays", 320
        ));
        context.setVariable("features", reportData.features());

        String templateName = "credit_report_" + lang;
        return templateEngine.process(templateName, context);
    }

    // Pdf 변환로직
    private byte[] convertHtmlToPdf(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);

            builder.useFont(
                    () -> {
                        java.io.InputStream inputStream = getClass().getResourceAsStream(MALGUN_GOTHIC_FONT_PATH);
                        if (inputStream == null) {
                            log.error("Font resource not found: {}", MALGUN_GOTHIC_FONT_PATH);
                            throw new CustomBaseException(ErrorBaseCode.PDF_FONT_ERROR);
                        }
                        return inputStream;
                    },
                    "Malgun Gothic"
            );

            builder.useFastMode();
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();

        } catch (IOException e) {
            log.error("PDF generation failed due to IOException.", e);
            throw new CustomBaseException(ErrorBaseCode.PDF_GENERATION_ERROR);
        }
    }
}
