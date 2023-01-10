package icu.agony.clm.aspect;

import icu.agony.clm.annotation.CheckCaptcha;
import icu.agony.clm.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckSafeAspect {

    private final CaptchaService captchaService;

    @Pointcut("@annotation(icu.agony.clm.annotation.CheckCaptcha)")
    void pointcut() {
    }

    @Around("pointcut()")
    Object checkSafe(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature method = (MethodSignature) pjp.getSignature();
        CheckCaptcha checkCaptcha = AnnotationUtils.getAnnotation(method.getMethod(), CheckCaptcha.class);
        captchaService.use(checkCaptcha.value());
        return pjp.proceed(pjp.getArgs());
    }

}
