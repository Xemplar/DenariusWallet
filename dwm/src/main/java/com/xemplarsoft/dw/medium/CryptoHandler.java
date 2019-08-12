package com.xemplarsoft.dw.medium;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public final class CryptoHandler {
    /*public static void main(String[] args) throws Exception {
        String plainText = "Hello World!";

        PrivateKey privateKey = loadPrivateKey(privkey);
        PublicKey publicKey = loadPublicKey(pubkey);

        String encryptedText = encryptMessage(plainText, publicKey);
        String descryptedText = decryptMessage(encryptedText, privateKey);

        System.out.println("input:" + plainText);
        System.out.println("encrypted:" + encryptedText);
        System.out.println("decrypted:" + descryptedText);


        System.out.println("Pri: " + savePrivateKey(privateKey));
        System.out.println("Pub: " + savePublicKey(publicKey));
    }*/

    private static Map<String,Object> getRSAKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        Map<String, Object> keys = new HashMap<>();
        keys.put("private", privateKey);
        keys.put("public", publicKey);
        return keys;
    }

    // Decrypt using RSA public key
    public static String decryptMessage(String encryptedText, PrivateKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
    }

    // Encrypt using RSA private key
    public static String encryptMessage(String plainText, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    // Decrypt using AES Key
    public static String decryptMessage(String encryptedText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
    }

    // Encrypt using AES Key
    public static String encryptMessage(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    public static String savePrivateKey(PrivateKey priv) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec spec = fact.getKeySpec(priv,
                PKCS8EncodedKeySpec.class);
        byte[] packed = spec.getEncoded();
        String key64 = Base64.getEncoder().encodeToString(packed);

        Arrays.fill(packed, (byte) 0);
        return key64;
    }


    public static String savePublicKey(PublicKey publ) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = fact.getKeySpec(publ,
                X509EncodedKeySpec.class);
        return Base64.getEncoder().encodeToString(spec.getEncoded());
    }

    public static PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
        byte[] clear = Base64.getDecoder().decode(key64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PrivateKey priv = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }


    public static PublicKey loadPublicKey(String stored) throws GeneralSecurityException {
        byte[] data = Base64.getDecoder().decode(stored);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePublic(spec);
    }

    public static SecretKey generateAES(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // for example
            SecretKey key = keyGen.generateKey();
            System.out.println("Sync key: " + Base64.getEncoder().encodeToString(key.getEncoded()));
            return key;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String generateRandomString(int length){
        String choices = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        String dat = "";

        Random rand = new Random();
        for(int i = 0; i < length; i++){
            int r = rand.nextInt(choices.length());
            dat += choices.charAt(r);
        }

        return dat;
    }
}
