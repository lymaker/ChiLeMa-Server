package icu.agony.clm.service;

import icu.agony.clm.controller.captcha.param.CaptchaGenerateParam;
import icu.agony.clm.controller.captcha.param.CaptchaVerifyParam;
import icu.agony.clm.controller.captcha.vo.CaptchaGenerateVO;

public interface CaptchaService {

    /**
     * 生成验证码
     *
     * @param param 请求参数
     * @return 验证码参数
     */
    CaptchaGenerateVO generate(CaptchaGenerateParam param);

    /**
     * 校验验证码
     *
     * @param param 请求参数
     */
    void verify(CaptchaVerifyParam param);

}
