package com.czl.console.backend.utils;

import org.springframework.util.ObjectUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * Author: CHEN ZHI LING
 * Date: 2023/11/22
 * Description:
 */
public class AesUtils {

    public static final String AES_MIDDLE_KEY = "chenZhiLing";

    //aes的key
    private static Key AES_KEY;

    /**
     * AES 加密
     * @author chenzaiyu
     * @param content 加密内容
     * @return String 加密后结果
     */
    public static String encryptByAes(String content) {
        if(ObjectUtils.isEmpty(content)){
            return null;
        }
        try {
            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getKey());
            byte[] result = cipher.doFinal(content.getBytes());
            return org.apache.commons.codec.binary.Base64.encodeBase64String(result);//通过Base64转码返回
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static String decryptByAes(String encryptedContent) {
        if (ObjectUtils.isEmpty(encryptedContent)){
            return null;
        }
        try {
            // 解密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, getKey());
            byte[] result = cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(encryptedContent));
            return ObjectUtils.isEmpty(result)?null:new String(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取AES对应的key
     * @return Key
     */
    public static Key getKey() {
        if (AES_KEY == null) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
                secureRandom.setSeed(AES_MIDDLE_KEY.getBytes());
                keyGenerator.init(128,secureRandom);
                SecretKey secretKey = keyGenerator.generateKey();
                byte[] byteKey = secretKey.getEncoded();
                // 转换KEY
                AES_KEY = new SecretKeySpec(byteKey, "AES");
                return AES_KEY;
            } catch (Exception e) {
                return null;
            }
        } else {
            return AES_KEY;
        }
    }
}
