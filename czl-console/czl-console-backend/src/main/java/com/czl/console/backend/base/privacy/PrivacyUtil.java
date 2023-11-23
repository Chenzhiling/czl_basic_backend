package com.czl.console.backend.base.privacy;

import org.springframework.util.ObjectUtils;

/**
 * Author: CHEN ZHI LING
 * Date: 2023/11/23
 * Description: 脱敏工具类
 */
public class PrivacyUtil {

    public static String hideEmail(String email) {
        if (ObjectUtils.isEmpty(email)) {
            return null;
        }
        return email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }

    public static String hidePhone(String phone) {
        if (ObjectUtils.isEmpty(phone)) {
            return null;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"); // 隐藏中间4位
    }


    public static String hideValue(String origin, int prefixNoMaskLen, int suffixNoMaskLen, String maskStr) {
        if (ObjectUtils.isEmpty(origin)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0, n = origin.length(); i < n; i++) {
            if (i < prefixNoMaskLen) {
                sb.append(origin.charAt(i));
                continue;
            }
            if (i > (n - suffixNoMaskLen - 1)) {
                sb.append(origin.charAt(i));
                continue;
            }
            sb.append(maskStr);
        }
        return sb.toString();
    }
}
