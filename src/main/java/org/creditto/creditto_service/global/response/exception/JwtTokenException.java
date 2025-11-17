package org.creditto.creditto_service.global.response.exception;

import org.creditto.creditto_service.global.response.error.ErrorCode;

public class JwtTokenException extends CustomBaseException{
    public JwtTokenException(final ErrorCode errorCode) {super(errorCode);}
}