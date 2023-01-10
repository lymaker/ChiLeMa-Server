package icu.agony.clm.util;

import org.junit.jupiter.api.RepeatedTest;

public class RandomUtilTest {

    @RepeatedTest(100)
    void makeString() {
        System.out.println(RandomUtil.makeString(4));
    }

}
