package icu.agony.clm.exception.handler;

import cn.dev33.satoken.exception.NotLoginException;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.exception.BasicClmException;
import icu.agony.clm.exception.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final boolean isDebug = log.isDebugEnabled();

    @ExceptionHandler
    ResponseEntity<String> basicCrazyException(BasicClmException e) {
        if (e instanceof BadRequestException) {
            if (isDebug) {
                e.printStackTrace();
            }
        } else {
            log.error(e.getMessage(), e);
        }
        return e.responseEntity();
    }

    @ExceptionHandler(NotLoginException.class)
    ResponseEntity<?> notLoginExceptionHandler() {
        if (isDebug) {
            log.debug("用户未登录");
        }
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler
    ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        FieldError param = e.getFieldError();
        if (param != null) {
            String name = param.getField();
            String message = param.getDefaultMessage();
            if (isDebug) {
                log.debug("参数 [{}] -> {}", name, message);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler
    ResponseEntity<?> unknownExceptionHandler(Exception e) {
        log.error("程序未知异常", e);
        return ResponseEntity.internalServerError().body("{}");
    }

}