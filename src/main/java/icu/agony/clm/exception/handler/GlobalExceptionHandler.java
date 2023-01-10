package icu.agony.clm.exception.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.NotSafeException;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.exception.BasicClmException;
import icu.agony.clm.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        UnauthorizedException unauthorizedException = new UnauthorizedException("用户未登录");
        unauthorizedException.message("无权访问");
        return unauthorizedException.responseEntity();
    }

    @ExceptionHandler
    ResponseEntity<?> notRoleException(NotRoleException e) {
        if (isDebug) {
            log.debug("用户未拥有 [{}] 角色", e.getRole());
        }
        BadRequestException badRequestException = new BadRequestException("用户无此角色");
        badRequestException.message("拒绝操作");
        return badRequestException.responseEntity();
    }

    @ExceptionHandler
    ResponseEntity<?> notSafeException(NotSafeException e) {
        if (isDebug) {
            log.debug("未通过 [{}] 二级验证", e.getService());
        }
        BadRequestException badRequestException = new BadRequestException("二级验证未通过");
        badRequestException.message("未通过验证");
        return badRequestException.responseEntity();
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<?> httpMessageNotReadableException() {
        BadRequestException badRequestException = new BadRequestException("非法请求参数");
        badRequestException.message("非法参数");
        return badRequestException.responseEntity();
    }

    @ExceptionHandler
    ResponseEntity<?> unknownExceptionHandler(Exception e) {
        log.error("程序未知异常", e);
        return ResponseEntity.internalServerError().build();
    }

}