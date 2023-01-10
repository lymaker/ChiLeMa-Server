package icu.agony.clm.controller.captcha.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CaptchaGenerateVO {

    /**
     * base64
     */
    private String image;

    /**
     * 验证码有效时长
     */
    private Long duration;

}
