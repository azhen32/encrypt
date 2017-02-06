package com.azhen.digest;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyHmac {
    private static String src = "imooc security hmac";

    public static void main(String[] args) {
        jdkHmacMD5();
        bcHmacMD5();
    }

    public static void jdkHmacMD5() {
        try {
            //初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
            //产生密钥,自动生成
            //SecretKey secretKey = keyGenerator.generateKey();
            //获得密钥
            //byte[] key = secretKey.getEncoded();
            //自己指定密钥,需要时偶数个字符
            byte[] key = Hex.decodeHex(new char[]{'a','a','a','a','a','a','a','a','a','a'});

            //还原密钥
            SecretKey restoreSecretKey = new SecretKeySpec(key,"HmacMD5");
            //实例化MAC
            Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
            //初始化Mac
            mac.init(restoreSecretKey);
            //执行摘要
            byte[] hmacMD5Bytes = mac.doFinal(src.getBytes());
            System.out.println("jdk hamcMD5: " + Hex.encodeHexString(hmacMD5Bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bcHmacMD5() {
        HMac hMac = new HMac(new MD5Digest());
        hMac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode("aaaaaaaaaa")));
        hMac.update(src.getBytes(),0,src.getBytes().length);

        byte[] hmacMD5Bytes = new byte[hMac.getMacSize()];  //执行摘要
        hMac.doFinal(hmacMD5Bytes,0);
        System.out.println("bc hmacMD5: " + Hex.encodeHexString(hmacMD5Bytes));
    }
}
