package icu.agony.clm.exception;

import org.springframework.http.HttpStatus;

/**
 * 用于表示服务器内部异常，可继承该异常实现异常定制处理
 */
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