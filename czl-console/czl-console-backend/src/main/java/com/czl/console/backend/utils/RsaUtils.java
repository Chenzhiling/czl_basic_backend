package com.czl.console.backend.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/10
 * Description:
 */
public class RsaUtils {


    /**
     * 公钥加密
     *
     * @param publicKeyText 公钥
     * @param text 待加密的文本
     * @return /
     */
    public static String encryptByPublicKey(String publicKeyText, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = doLongerCipherFinal(Cipher.ENCRYPT_MODE, cipher, text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 私钥解密
     * @param privateKeyText 私钥
     * @param text 待解密的文本
     * @return /
     * @throws Exception /
     */
    public static String decryptByPrivateKey(String privateKeyText, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = doLongerCipherFinal(Cipher.DECRYPT_MODE, cipher, Base64.decodeBase64(text));
        return new String(result);
    }

    private static byte[] doLongerCipherFinal(int opMode,Cipher cipher, byte[] source) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (opMode == Cipher.DECRYPT_MODE) {
            out.write(cipher.doFinal(source));
        } else {
            int offset = 0;
            int totalSize = source.length;
            while (totalSize - offset > 0) {
                int size = Math.min(cipher.getOutputSize(0) - 11, totalSize - offset);
                out.write(cipher.doFinal(source, offset, size));
                offset += size;
            }
        }
        out.close();
        return out.toByteArray();
    }
}
