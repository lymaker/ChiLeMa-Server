package icu.agony.clm.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BasicClmException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    protected HttpStatus httpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

}
