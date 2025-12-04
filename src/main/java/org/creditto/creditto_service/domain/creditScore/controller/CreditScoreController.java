package org.creditto.creditto_service.domain.creditScore.controller;

import lombok.RequiredArgsConstructor;
import org.creditto.creditto_service.domain.creditScore.dto.CreditScorePredictReq;
import org.creditto.creditto_service.domain.creditScore.dto.CreditScoreReq;
import org.creditto.creditto_service.domain.creditScore.service.CreditScoreService;
import org.creditto.creditto_service.global.infra.creditrating.CreditScoreHistoryRes;
import org.creditto.creditto_service.global.infra.creditrating.CreditScorePredictRes;
import org.creditto.creditto_service.global.infra.creditrating.CreditScoreRes;
import org.creditto.creditto_service.global.response.error.ErrorBaseCode;
import org.creditto.creditto_service.global.response.exception.CustomBaseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/credit-score")
@RequiredArgsConstructor
public class CreditScoreController {

    private final CreditScoreService creditScoreService;

    @PostMapping()
    public CreditScoreRes calculateCreditScore(@RequestBody CreditScoreReq request) {
        return creditScoreService.calculateCreditScore(request);
    }

    @GetMapping("/{userId}")
    public CreditScoreRes getCreditScore(@PathVariable Long userId) {
        return creditScoreService.getCreditScore(userId);
    }

    @GetMapping("/history/{userId}")
    public CreditScoreHistoryRes getCreditScoreHistory(@PathVariable Long userId) {
        return creditScoreService.getCreditScoreHistory(userId);
    }

    @PostMapping("/prediction")
    public CreditScorePredictRes predictCreditScore(@RequestBody CreditScorePredictReq request) {
        return creditScoreService.predictCreditScore(request);
    }

    @GetMapping(
            value = "/report/{lang}/pdf/{userId}",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> downloadCreditReportPdf(
            @PathVariable String lang,
            @PathVariable Long userId) {

        if (!"ko".equalsIgnoreCase(lang) && !"en".equalsIgnoreCase(lang)) {
            throw new CustomBaseException(ErrorBaseCode.BAD_REQUEST);
        }

        byte[] pdfBytes = creditScoreService.generateCreditScoreReportPdf(userId, lang);

        String langCode = "ko".equalsIgnoreCase(lang) ? "ko" : "en";
        String safeDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String fileName = "credit_report_%s_%s.pdf".formatted(langCode, safeDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "attachment",
                fileName);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}