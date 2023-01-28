package com.predictor.library.utils;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CNSHAUtils {

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
