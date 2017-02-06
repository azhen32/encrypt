package com.azhen.digest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class MyMD {
    private static String src = "imooc md security";
    public static void main(String[] args) {
        jdkMD2();
        jdkUsebcMD4();
        jdkMD5();
        bcMD5();
        ccMD5();
        ccMD2();
    }

    public static void jdkMD5() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(src.getBytes());
            //需要转换为16进制
            System.out.println("JDK MD5: " + Hex.encodeHexString(digest));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void jdkMD2() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] digest = md.digest(src.getBytes());
            //需要转换为16进制
            System.out.println("JDK MD2: " + Hex.encodeHexString(digest));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void jdkUsebcMD4() {

        try {
            Security.addProvider(new BouncyCastleProvider());
            MessageDigest md = MessageDigest.getInstance("MD4");
            byte[] digest = md.digest(src.getBytes());
            //需要转换为16进制
            System.out.println("jdkUsebcMD4: " + Hex.encodeHexString(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void bcMD5() {
        Digest digest = new MD5Digest();
        digest.update(src.getBytes(),0,src.getBytes().length);
        byte[] md4Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md4Bytes,0);
        System.out.println("BC MD5: " + org.bouncycastle.util.encoders.Hex.toHexString(md4Bytes));
    }

    public static void ccMD5() {
        System.out.println("ccMD5: " + DigestUtils.md5Hex(src.getBytes()));
    }

    public static void ccMD2() {
        System.out.println("ccMD2: " + DigestUtils.md2Hex(src.getBytes()));
    }
}
