package icu.agony.clm.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import icu.agony.clm.config.properties.ClmDefaultProperties;
import icu.agony.clm.consts.CaptchaType;
import icu.agony.clm.controller.captcha.param.CaptchaGenerateParam;
import icu.agony.clm.controller.captcha.param.CaptchaVerifyParam;
import icu.agony.clm.controller.captcha.vo.CaptchaGenerateVO;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.exception.InternalServerException;
import icu.agony.clm.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;


@Service
@RequiredArgsConstructor
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {

    private final DefaultKaptcha kaptcha;

    private final ClmDefaultProperties defaultProperties;

    private final HttpSession session;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public CaptchaGenerateVO generate(CaptchaGenerateParam param) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            // region 验证码生成
            String captchaText = kaptcha.createText();
            BufferedImage captchaImage = kaptcha.createImage(captchaText);
            ImageIO.write(captchaImage, "jpg", stream);
            String imageBase64 = Base64Utils.encodeToString(stream.toByteArray());
            stream.flush();
            // endregion

            // region 验证码写入redis
            Duration duration = defaultProperties.getCaptchaDuration();
            String redisKey = redisKey(param.getType());
            redisTemplate.opsForValue().set(redisKey, captchaText, duration);
            // endregion

            // region 封装返回值
            CaptchaGenerateVO captchaGenerateVO = new CaptchaGenerateVO();
            captchaGenerateVO.setImage("data:image/png;base64," + imageBase64);
            captchaGenerateVO.setDuration(duration.toSeconds());
            return captchaGenerateVO;
            // endregion
        } catch (IOException e) {
            InternalServerException internalServerException = new InternalServerException("生成验证码失败", e);
            internalServerException.message("获取验证码失败");
            throw internalServerException;
        }
    }

    @Override
    public void verify(CaptchaVerifyParam param) {
        String redisKey = redisKey(param.getType());
        Object captchaText = redisTemplate.opsForValue().get(redisKey);
        // 验证码不存在
        if (captchaText == null) {
            BadRequestException badRequestException = new BadRequestException("验证码过期");
            badRequestException.message("验证码过期");
            throw badRequestException;
        }
        // 验证码错误
        if (!captchaText.equals(param.getValue())) {
            BadRequestException badRequestException = new BadRequestException("验证码错误");
            badRequestException.message("验证码错误");
            throw badRequestException;
        }
        CaptchaType type = param.getType();
        StpUtil.openSafe(type.getNickname(), type.getTimeout().toSeconds());
    }

    private String redisKey(CaptchaType type) {
        return String.format("captcha:%s:%s", session.getId(), type);
    }

}
