package icu.agony.clm.controller.captcha;

import icu.agony.clm.controller.captcha.param.CaptchaGenerateParam;
import icu.agony.clm.controller.captcha.param.CaptchaVerifyParam;
import icu.agony.clm.controller.captcha.vo.CaptchaGenerateVO;
import icu.agony.clm.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    @GetMapping("/generate")
    CaptchaGenerateVO generate(@RequestBody @Validated CaptchaGenerateParam param) {
        return captchaService.generate(param);
    }

    @PostMapping("/verify")
    void verify(@RequestBody @Validated CaptchaVerifyParam param) {
        captchaService.verify(param);
    }

}
