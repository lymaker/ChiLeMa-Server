package icu.agony.clm.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

/**
 * 验证码类型，用于区分业务场景
 */
@Getter
@AllArgsConstructor
public enum CaptchaType {

    LOGIN("login", Duration.ofSeconds(60));

    private final String nickname;

    private final Duration timeout;

}
