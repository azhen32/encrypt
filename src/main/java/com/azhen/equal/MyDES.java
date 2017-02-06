package com.azhen.equal;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Security;

public class MyDES {

    private static String src = "imooc security des";
    public static void main(String[] args) {
        jdkDES();
        bdDES();
    }

    public static void jdkDES() {
        try {
            //生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);  //指定key的size
            SecretKey secretKey = keyGenerator.generateKey();   //key是随机产生的
            byte[] keyBytes = secretKey.getEncoded();

            //key转换
            DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey converSecretKey = keyFactory.generateSecret(desKeySpec);

            //加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); //加密方式/工作方式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE,converSecretKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk des encrypt: " + Hex.encodeHexString(result));

            //解密
            cipher.init(Cipher.DECRYPT_MODE,converSecretKey);
            result = cipher.doFinal(result);
            System.out.println("jdk des decrypt: " + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bdDES() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            //生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES","BC");   //指定使用BC的provider
            keyGenerator.init(56);  //指定key的size
            SecretKey secretKey = keyGenerator.generateKey();   //key是随机产生的
            byte[] keyBytes = secretKey.getEncoded();

            //key转换
            DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey converSecretKey = keyFactory.generateSecret(desKeySpec);

            //加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); //加密方式/工作方式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE,converSecretKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("bc des encrypt: " + Hex.encodeHexString(result));

            //解密
            cipher.init(Cipher.DECRYPT_MODE,converSecretKey);
            result = cipher.doFinal(result);
            System.out.println("bc des decrypt: " + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
