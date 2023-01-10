package icu.agony.clm.annotation;

import icu.agony.clm.consts.CaptchaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckCaptcha {

    CaptchaType value();

}
