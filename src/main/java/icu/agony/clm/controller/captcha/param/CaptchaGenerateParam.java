package icu.agony.clm.controller.captcha.param;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import icu.agony.clm.consts.CaptchaType;
import icu.agony.clm.request.convert.json.CaptchaTypeJsonConvert;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CaptchaGenerateParam {

    @NotNull
    @JsonDeserialize(using = CaptchaTypeJsonConvert.class)
    private CaptchaType type;

}
