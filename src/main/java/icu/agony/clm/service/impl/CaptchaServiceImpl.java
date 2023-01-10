package icu.agony.clm.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import icu.agony.clm.config.properties.ClmCaptchaProperties;
import icu.agony.clm.consts.CaptchaType;
import icu.agony.clm.controller.captcha.param.CaptchaGenerateParam;
import icu.agony.clm.controller.captcha.param.CaptchaVerifyParam;
import icu.agony.clm.controller.captcha.vo.CaptchaGenerateVO;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.exception.InternalServerException;
import icu.agony.clm.service.CaptchaService;
import icu.agony.clm.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
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

    private final boolean isDebug = log.isDebugEnabled();

    private final DefaultKaptcha kaptcha;

    private final ClmCaptchaProperties captchaProperties;

    private final HttpSession session;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public CaptchaGenerateVO generate(CaptchaGenerateParam param) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            // region 验证码生成
            String captchaText = RandomUtil.makeString(captchaProperties.getLength());
            BufferedImage captchaImage = kaptcha.createImage(captchaText);
            ImageIO.write(captchaImage, "jpg", stream);
            String imageBase64 = Base64Utils.encodeToString(stream.toByteArray());
            stream.flush();
            if (isDebug) {
                log.debug("session: [{}], 验证码：[{}]", session.getId(), captchaText);
            }
            // endregion

            // region 验证码写入redis
            Duration duration = captchaProperties.getDuration();
            String redisKey = redisKey(param.getType());
            redisTemplate.opsForValue().set(redisKey, captchaText, duration.plusSeconds(15));
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
        Object captcha = redisTemplate.opsForValue().get(redisKey);
        if (isDebug) {
            log.debug("验证码校验 -> type：[{}]，captcha：[{}]，actual：[{}]", param.getType(), param.getValue(), captcha);
        }

        // region 验证码不存在
        if (captcha == null) {
            BadRequestException badRequestException = new BadRequestException("验证码过期");
            badRequestException.message("验证码过期");
            throw badRequestException;
        }
        // endregion

        // region 验证码错误
        String captchaText = ((String) captcha);
        String captchaTemp = param.getValue();
        boolean result = captchaProperties.getStrict() ? captchaText.equals(captchaTemp) : captchaText.equalsIgnoreCase(captchaTemp);
        if (!result) {
            BadRequestException badRequestException = new BadRequestException("验证码错误");
            badRequestException.message("验证码错误");
            throw badRequestException;
        }
        // endregion

        // region 验证通过
        CaptchaType type = param.getType();
        redisTemplate.delete(redisKey);
        redisTemplate.opsForValue().set(useKey(type), DateTime.now().getMillis(), Duration.ofSeconds(60));
        // endregion
    }

    @Override
    public void use(CaptchaType type) {
        String useKey = useKey(type);
        if (redisTemplate.opsForValue().get(useKey) == null) {
            BadRequestException badRequestException = new BadRequestException("未通过二级验证");
            badRequestException.message("未通过验证");
            throw badRequestException;
        }
        redisTemplate.delete(useKey);
    }

    private String redisKey(CaptchaType type) {
        return String.format("captcha:%s:%s", session.getId(), type);
    }

    private String useKey(CaptchaType type) {
        return String.format("use:%s:%s", session.getId(), type);
    }

}
