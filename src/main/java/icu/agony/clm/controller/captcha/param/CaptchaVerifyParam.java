package icu.agony.clm.controller.captcha.param;

import icu.agony.clm.consts.CaptchaType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CaptchaVerifyParam {

    @NotNull
    private CaptchaType type;

    @NotBlank
    private String value;

}
