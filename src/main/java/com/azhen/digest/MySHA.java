package com.azhen.digest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class MySHA {
    private static String src = "imooc security sha";
    public static void main(String[] args) {
        jdkSHA1();
        bcSHA1();
        bdSHA224();
        bcSHA224_Provider();
        ccSHA1();
    }

    public static void jdkSHA1() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] digest = md.digest(src.getBytes());
            System.out.println("jdk sha-1: " + Hex.encodeHexString(digest));
           /* md.update(src.getBytes());
            System.out.println("jdk sha-1: " + Hex.encodeHexString(md.digest()));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bcSHA1() {
        Digest digest = new SHA1Digest();
        digest.update(src.getBytes(),0,src.getBytes().length);
        byte[] sha1Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(sha1Bytes,0);
        System.out.println("bc sha-1: " + org.bouncycastle.util.encoders.Hex.toHexString(sha1Bytes));
    }

    public static void bdSHA224() {
        Digest digest = new SHA224Digest();
        digest.update(src.getBytes(),0,src.getBytes().length);
        byte[] sha1Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(sha1Bytes,0);
        System.out.println("bc sha-224: " + org.bouncycastle.util.encoders.Hex.toHexString(sha1Bytes));
    }

    public static void bcSHA224_Provider() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            MessageDigest md = MessageDigest.getInstance("SHA224");
            byte[] digest = md.digest(src.getBytes());
            //需要转换为16进制
            System.out.println("provider sha-224: " + Hex.encodeHexString(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void ccSHA1() {
        System.out.println("cc SHA1 - 1: " + DigestUtils.sha1Hex(src.getBytes()));
        System.out.println("cc SHA1 - 2: " + DigestUtils.sha1Hex(src));
    }
}
