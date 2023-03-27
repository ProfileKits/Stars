package com.predictor.library.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Create by zq
 * DES加密类
 * 采用对称加密
 * 加密后的内容一般采用base64进行传输
 * <p>
 * IvParameterSpec(byte[] iv)
 * iv:具有IV的缓冲区
 * IvParameterSpec(byte[] iv,int offset,int len)
 * iv:
 * offset:iv中的偏移量iv[offset]
 * len:IV字节的数目
 */

/**
 * 1.CBS为工作模式
 * DES一共有电子密码本模式（ECB）、加密分组链接模式（CBC）、加密反馈模式（CFB）和输出反馈模式（OFB）四种模式
 * 2.PKCS5Padding为填充模式
 * 3.cipher.init(ipher.ENCRYPT_MODE, key, zeroIv)，zeroIv为初始化向量
 * <p>
 * 注意:三者缺一不可，如果不指定，程序会调用默认实现，而默认实现与平台有关，
 * 可能导致在客户端中加密的内容与服务器加密的内容不一致
 */
public class CNDES {

    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};

    /**
     * DESede(3DES) 要求密钥长度为128 || 192 bits 因此我们需要对生成的密钥进行截取在加解密
     * 但是ios端并未对密钥进行截取(即ios端用长度为三十多个字节的密钥进行加解密，也能够解密Android端发送过去的消息)
     * 同时，Android用截取的密钥解密ios端发送过来的消息，也能正常解密
     * 结论：ios端内部可能对密钥进行了处理，根据加密的方式内部进行了截取
     */
    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        encryptKey = encryptKey.substring(0, 24);
        SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey.getBytes(), "DESede");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        // 加密
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return CNBase64.encode(encryptedData);
    }

    public static String decryptDES(String decryptString, String decryptKey) throws Exception {
        decryptKey = decryptKey.substring(0, 24);
        SecretKeySpec secretKeySpec = new SecretKeySpec(decryptKey.getBytes(), "DESede");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        // 解密
        byte decryptedData[] = cipher.doFinal(CNBase64.decode(decryptString));
        return new String(decryptedData);
    }


    /*
     * 生成密钥
     */
    public static byte[] initKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
    }

    /*
     * DES 加密
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "DES");

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherBytes = cipher.doFinal(data);
        return cipherBytes;
    }

    /*
     * DES 解密
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "DES");

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] plainBytes = cipher.doFinal(data);
        return plainBytes;
    }


    //byte[]转成String
    public static String byte2String(byte[] bytes) {
        String str = "";
        try {
            str = new String(bytes, "UTF-8"); // 使用 UTF-8 编码将 byte[] 转换为 String
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
