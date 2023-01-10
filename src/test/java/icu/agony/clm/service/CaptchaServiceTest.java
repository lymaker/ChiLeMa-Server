package icu.agony.clm.service;

import icu.agony.clm.Application;
import icu.agony.clm.consts.CaptchaType;
import icu.agony.clm.controller.captcha.param.CaptchaGenerateParam;
import icu.agony.clm.controller.captcha.vo.CaptchaGenerateVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = Application.class)
public class CaptchaServiceTest {

    @Resource
    private CaptchaService captchaService;

    @Test
    void generate() {
        CaptchaGenerateParam param = new CaptchaGenerateParam();
        param.setType(CaptchaType.LOGIN);
        CaptchaGenerateVO vo = captchaService.generate(param);
        System.out.println(vo);
    }

}
