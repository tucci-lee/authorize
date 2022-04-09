package com.tuccicode.authorize.domain.open.constant;

import java.util.UUID;

/**
 * 文件存储的常量
 *
 * @author tucci.lee
 */
public class OpenFileConst {

    public static final String AVATAR_PREFIX = "profile/avatar/%s/";

    /**
     * 获取文件后缀名
     *
     * @param filename 文件名
     * @return 后缀名
     */
    public static String getSuffixName(String filename) {
        if (filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0) {
            return filename.substring(filename.lastIndexOf(".") + 1);
        }
        return "";
    }

    /**
     * 获取文件名
     *
     * @param prefix   前缀
     * @param filename 原文件名
     * @return 新文件名
     */
    public static String formatName(String prefix, String filename) {
        String suffix = getSuffixName(filename);
        String newFilename = prefix + UUID.randomUUID();

        if (!suffix.isEmpty()) {
            return newFilename + "." + suffix;
        }
        return newFilename;
    }
}
