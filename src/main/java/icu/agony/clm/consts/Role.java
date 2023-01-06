package icu.agony.clm.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    CONSUMER(1, "consumer"),

    PROVIDER(2, "provider"), ;

    private final Integer id;

    private final String nickname;

}
