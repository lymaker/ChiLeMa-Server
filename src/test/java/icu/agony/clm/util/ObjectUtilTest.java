package icu.agony.clm.util;

import icu.agony.clm.controller.user.param.UserUpdateParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectUtilTest {

    @Test
    void isEmpty() {
        UserUpdateParam param = new UserUpdateParam();
        Assertions.assertTrue(ObjectUtil.isEmpty(param));
    }

}
