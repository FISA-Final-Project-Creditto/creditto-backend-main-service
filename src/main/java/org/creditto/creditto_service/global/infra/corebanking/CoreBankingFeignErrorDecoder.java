package org.creditto.creditto_service.global.infra.corebanking;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.creditto.creditto_service.global.response.error.ErrorMessage;
import org.creditto.creditto_service.global.response.exception.CoreBankingFeignException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.creditto.creditto_service.global.common.Constants.CODE;
import static org.creditto.creditto_service.global.common.Constants.MESSAGE;

/**
 * CoreBanking OpenFeign 오류 응답을 Creditto 예외로 변환하는 디코더.
 * CoreBanking의 상태 코드와 메시지를 그대로 {@link CoreBankingFeignException}으로 전달한다.
 */
@Slf4j
@RequiredArgsConstructor
public class CoreBankingFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        int httpStatus = response != null
                ? response.status()
                : HttpStatus.INTERNAL_SERVER_ERROR.value();

        String responseBody = readResponseBody(response);
        log.warn("[CoreBankingFeignErrorDecoder] CoreBanking OpenFeign 에러 / methodKey={}, status={}, body={}", methodKey, httpStatus, responseBody);

        if (!StringUtils.hasText(responseBody)) {
            return new CoreBankingFeignException(httpStatus, null, ErrorMessage.SERVER_ERROR);
        }

        try {
            JsonNode node = objectMapper.readTree(responseBody);
            Integer code = node.hasNonNull(CODE)
                    ? node.get(CODE).asInt()
                    : null;

            String message = node.hasNonNull(MESSAGE)
                    ? node.get(MESSAGE).asText()
                    : ErrorMessage.SERVER_ERROR;

            return new CoreBankingFeignException(httpStatus, code, message);
        } catch (IOException e) {
            log.error("[CoreBankingFeignErrorDecoder] Response Body 파싱 에러", e);
            return new CoreBankingFeignException(httpStatus, null, ErrorMessage.SERVER_ERROR);
        }
    }

    private String readResponseBody(Response response) {
        if (response == null || response.body() == null) {
            return null;
        }

        try (InputStream inputStream = response.body().asInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("[CoreBankingFeignErrorDecoder] Response Body 파싱 에러", e);
            return null;
        }
    }
}
