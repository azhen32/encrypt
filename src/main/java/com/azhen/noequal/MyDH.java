package com.azhen.noequal;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

public class MyDH {
    private static String src = "imooc security dh";
    public static void main(String[] args) {
        jdkDH();
    }
    public static void jdkDH() {
        //1.初始化发送方密钥
        try {
            KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
            senderKeyPairGenerator.initialize(512);
            KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
            byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded(); //发送方公钥，发送给接受方（网络、文件）

            //2.初始化接收方密钥
            KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
            PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
            DHParameterSpec dhParameterSpec = ((DHPublicKey) receiverPublicKey).getParams();
            KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
            receiverKeyPairGenerator.initialize(dhParameterSpec);
            KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();   //密钥初始化
            PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
            byte[] receiverPublicKeyEnc = receiverKeyPair.getPublic().getEncoded();

            //3.密钥构建
            KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
            receiverKeyAgreement.init(receiverPrivateKey);
            receiverKeyAgreement.doPhase(receiverPublicKey,true);
            SecretKey receiverDestKey = receiverKeyAgreement.generateSecret("DES"); //构建公钥

            KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
            x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
            PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
            KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
            senderKeyAgreement.init(senderKeyPair.getPrivate());
            senderKeyAgreement.doPhase(senderPublicKey,true);

            SecretKey senderDesKey = senderKeyAgreement.generateSecret("DES");  //构建密钥
            if(Objects.equals(receiverDestKey,senderDesKey)) {
                System.out.println("双方密钥相同");
            }

            //4.加密
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE,senderDesKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk dh encrypt: " + Base64.encodeBase64String(result));

            //5.解密
            cipher.init(Cipher.DECRYPT_MODE,receiverDestKey);
            result = cipher.doFinal(result);
            System.out.println("jdk dh decrypt: " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
