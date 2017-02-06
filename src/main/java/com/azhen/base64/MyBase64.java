package com.azhen.base64;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;


public class MyBase64 {
    private static String src = "imooc security base65";
    public static void main(String[] args) {
        int times = 100000;
        long startTime = 0L;
        startTime = System.currentTimeMillis();
        for(int i = 0; i < times; i ++) {
            jdkBase64();
        }
        System.out.println(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        for(int i = 0; i < times; i ++) {
            commonsCodesBase64();
        }
        System.out.println(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        for(int i = 0; i < times; i ++) {
            bouncyCastleBase64();
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    public static void jdkBase64() {
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            String encode = encoder.encode(src.getBytes());
            //System.out.println("encode: " + encode);

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decode = decoder.decodeBuffer(encode);
            //System.out.println("decode: " + new String(decode));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void commonsCodesBase64() {
        byte[] encode = Base64.encodeBase64(src.getBytes());
        //System.out.println("encode: " + new String(encode));

        byte[] decode = Base64.decodeBase64(encode);
        //System.out.println("decode: " + new String(decode));
    }

    public static void bouncyCastleBase64() {
        byte[] encode = org.bouncycastle.util.encoders.Base64.encode(src.getBytes());
        //System.out.println("encode: " + new String(encode));

        byte[] decode = org.bouncycastle.util.encoders.Base64.decode(encode);
        //System.out.println("decode: " + new String(decode));
    }
}
