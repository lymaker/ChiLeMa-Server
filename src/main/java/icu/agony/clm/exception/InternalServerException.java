package icu.agony.clm.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends BasicClmException {

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    protected HttpStatus httpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}