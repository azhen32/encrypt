package com.azhen.equal;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.SecureRandom;
import java.security.Security;

public class My3DES {
    private static String src = "imooc security des";

    public static void main(String[] args) {
        jdk3DES();
        bd3DES();
    }

    public static void jdk3DES() {
        try {
            //生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            //keyGenerator.init(168);  //指定key的size
            keyGenerator.init(new SecureRandom());  //不需手工指定key的长度，自动生成默认长度的key
            SecretKey secretKey = keyGenerator.generateKey();   //key是随机产生的
            byte[] keyBytes = secretKey.getEncoded();

            //key转换
            DESedeKeySpec desKeySpec = new DESedeKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey converSecretKey = keyFactory.generateSecret(desKeySpec);

            //加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding"); //加密方式/工作方式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE,converSecretKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk 3des encrypt: " + Hex.encodeHexString(result));

            //解密
            cipher.init(Cipher.DECRYPT_MODE,converSecretKey);
            result = cipher.doFinal(result);
            System.out.println("jdk 3des decrypt: " + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bd3DES() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            //生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede","BC");   //指定使用BC的provider
            keyGenerator.init(168);  //指定key的size
            //keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();   //key是随机产生的
            byte[] keyBytes = secretKey.getEncoded();

            //key转换
            DESedeKeySpec desKeySpec = new DESedeKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey converSecretKey = keyFactory.generateSecret(desKeySpec);

            //加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding"); //加密方式/工作方式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE,converSecretKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("bc 3des encrypt: " + Hex.encodeHexString(result));

            //解密
            cipher.init(Cipher.DECRYPT_MODE,converSecretKey);
            result = cipher.doFinal(result);
            System.out.println("bc 3des decrypt: " + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
