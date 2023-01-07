package icu.agony.clm.util;

import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

public final class ObjectUtil {

    private ObjectUtil() {
    }

    public static boolean isEmpty(Object value, String... ignores) {
        Class<?> type = value.getClass();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            if (Arrays.binarySearch(ignores, field.getName()) >= 0) {
                continue;
            }
            field.setAccessible(true);
            Object fieldValue = ReflectionUtils.getField(field, value);
            if (!ObjectUtils.isEmpty(fieldValue)) {
                return false;
            }
        }
        return true;
    }

}
