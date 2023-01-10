package icu.agony.clm.util;

import java.util.Random;

public final class RandomUtil {

    private static final Random RANDOM = new Random();

    private static final char[] RANGE = {
        'A', 'B', 'C', 'D', 'E', 'F',
        'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'X',
        'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h', 'i', 'j', 'k', 'l',
        'm', 'n', 'o', 'p', 'q', 'r',
        's', 't', 'u', 'v', 'w', 'x',
        'y', 'x',
        '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9'
    };

    private RandomUtil() {
    }

    public static String makeString(int length) {
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = RANGE[RANDOM.nextInt(62)];
        }
        return String.valueOf(result);
    }

}
