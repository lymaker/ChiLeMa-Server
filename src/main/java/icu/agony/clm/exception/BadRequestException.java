package icu.agony.clm.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BasicClmException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    protected HttpStatus httpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
