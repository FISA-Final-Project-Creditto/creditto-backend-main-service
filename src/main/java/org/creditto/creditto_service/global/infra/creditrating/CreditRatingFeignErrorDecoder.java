package org.creditto.creditto_service.global.infra.creditrating;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.creditto.creditto_service.global.response.error.ErrorMessage;
import org.creditto.creditto_service.global.response.exception.CreditRatingFeignException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.creditto.creditto_service.global.common.Constants.CODE;
import static org.creditto.creditto_service.global.common.Constants.MESSAGE;


@Slf4j
@RequiredArgsConstructor
public class CreditRatingFeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        int httpStatus = response != null
                ? response.status()
                : HttpStatus.INTERNAL_SERVER_ERROR.value();

        String responseBody = readResponseBody(response);
        log.warn("[CreditRatingFeignErrorDecoder] CreditRating OpenFeign 에러 / methodKey={}, status={}, body={}", methodKey, httpStatus, responseBody);

        if (!StringUtils.hasText(responseBody)) {
            return new CreditRatingFeignException(httpStatus, null, ErrorMessage.SERVER_ERROR);
        }

        try {
            JsonNode node = objectMapper.readTree(responseBody);
            Integer code = node.hasNonNull(CODE)
                    ? node.get(CODE).asInt()
                    : null;

            String message = node.hasNonNull(MESSAGE)
                    ? node.get(MESSAGE).asText()
                    : ErrorMessage.SERVER_ERROR;

            return new CreditRatingFeignException(httpStatus, code, message);
        } catch (IOException e) {
            log.error("[CreditRatingFeignErrorDecoder] Response Body 파싱 에러", e);
            return new CreditRatingFeignException(httpStatus, null, ErrorMessage.SERVER_ERROR);
        }
    }

    private String readResponseBody(Response response) {
        if (response == null || response.body() == null) {
            return null;
        }

        try (InputStream inputStream = response.body().asInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("[CreditRatingFeignErrorDecoder] Response Body 파싱 에러", e);
            return null;
        }
    }
}
