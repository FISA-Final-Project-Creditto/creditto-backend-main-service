package org.creditto.creditto_service.global.response.exception;

import org.creditto.creditto_service.global.response.error.ErrorCode;

public class JwtInvalidException extends JwtTokenException{
    public JwtInvalidException(ErrorCode errorCode) {super(errorCode);}
}
