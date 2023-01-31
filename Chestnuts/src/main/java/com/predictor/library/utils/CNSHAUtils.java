package com.predictor.library.utils;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CNSHAUtils {

    private CNSHAUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * SHA-512 加密
     * @param data
     * @return
     */
    public static String encryptSHA(byte[] data) {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA-512");
            sha.update(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] resultBytes = sha.digest();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < resultBytes.length; i++) {
            if (Integer.toHexString(0xFF & resultBytes[i]).length() == 1) {
                builder.append("0").append(
                        Integer.toHexString(0xFF & resultBytes[i]));
            } else {
                builder.append(Integer.toHexString(0xFF & resultBytes[i]));
            }
        }
        return builder.toString();
    }

    //生成秘钥算法
    public String getSHA256SecretKey(String message){
        String result="null";
        try {
            //初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            //产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获取密钥
            byte[] key = secretKey.getEncoded();

            //还原密钥
            SecretKey restoreSecretKey = new SecretKeySpec(key, "HmacSHA256");
            //实例化MAC
            Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
            //初始化MAC
            mac.init(restoreSecretKey);
            //执行摘要
            byte[] hmacSHA256Bytes = mac.doFinal(message.getBytes());

            result = Base64.encodeToString(hmacSHA256Bytes, Base64.DEFAULT);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

}
