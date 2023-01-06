package icu.agony.clm.util;

import org.springframework.lang.NonNull;

import java.io.File;

public final class FileUtil {

    private FileUtil() {}

    public static String type(@NonNull File file) {
        String fileName = file.getName();
        return type(fileName);
    }

    public static String type(String fileName) {
        int index = fileName.lastIndexOf(".") + 1;
        return fileName.substring(index);
    }

}
