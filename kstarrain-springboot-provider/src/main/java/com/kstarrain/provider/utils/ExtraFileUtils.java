package com.kstarrain.provider.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author: DongYu
 * @create: 2019-11-07 08:57
 * @description:
 */
@Slf4j
public class ExtraFileUtils{

    public static boolean isCsvFile(String filePath) {
        String suffix = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
        return ".csv".equals(suffix);
    }

    public static boolean deleteExistFile(File file) {
        try {
            if (file != null && file.isFile() && file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            log.error("Delete File Error ! Path : {}", file, e);
        }
        return false;
    }

}
