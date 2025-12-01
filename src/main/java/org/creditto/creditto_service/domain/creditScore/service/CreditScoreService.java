package org.creditto.creditto_service.domain.creditScore.service;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.creditScore.dto.CreditScorePredictReq;
import org.creditto.creditto_service.domain.creditScore.dto.CreditScoreReq;
import org.creditto.creditto_service.global.infra.auth.ClientRes;
import org.creditto.creditto_service.global.infra.creditrating.*;
import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.creditto.creditto_service.global.response.exception.CustomBaseException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import org.creditto.creditto_service.global.infra.auth.AuthFeignClient;

@Service
@RequiredArgsConstructor
public class CreditScoreService {

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
    public byte[] generateCreditScoreReportPdf(Long userId) {
        try {
            String html = buildReportHtml(userId);
            return convertHtmlToPdf(html);
        } catch (CustomBaseException e) { // 폰트 에러
            throw e;
        } catch (Exception e) { // 이외의 에러
            throw new CustomBaseException(ErrorBaseCode.PDF_GENERATION_ERROR);
        }
    }

    // HTML 생성 로직 (템플릿 + 데이터 바인딩)
    private String buildReportHtml(Long userId) {
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

        return templateEngine.process("credit_report", context);
    }

    // HTML → PDF 변환 (렌더링)
    private byte[] convertHtmlToPdf(String html) throws IOException, DocumentException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();

            configureFonts(renderer);

            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            renderer.finishPDF();

            return outputStream.toByteArray();
        }
    }

    // 폰트 등록
    private void configureFonts(ITextRenderer renderer) {
        try {
            URL fontUrl = Objects.requireNonNull(getClass().getResource(MALGUN_GOTHIC_FONT_PATH));
            String fontPath = fontUrl.toExternalForm();

            renderer.getFontResolver().addFont(
                    fontPath,
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );
        } catch (Exception e) {
            throw new CustomBaseException(ErrorBaseCode.PDF_FONT_ERROR);
        }
    }
}
