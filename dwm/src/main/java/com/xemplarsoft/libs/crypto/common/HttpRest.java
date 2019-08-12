package com.xemplarsoft.libs.crypto.common;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * Created by Rohan on 8/22/2017.
 */
public abstract class HttpRest {
    protected final HttpClient httpclient;
    protected byte[] pass, enckey;
    protected String url;
    protected String user;
    protected Exception e;

    public HttpRest(String pubKey, char[] privKey, char[] enckey, String url){
        this.httpclient = HttpClients.createDefault();
        this.url = url;
        this.user = pubKey;
        this.pass = toBytes(privKey);
        this.enckey = toBytes(enckey);

        Arrays.fill(privKey, '\u0000');
        Arrays.fill(enckey, '\u0000');
    }

    public Exception getError(){
        return e;
    }
    public void setURL(String webURL){
        this.url = webURL;
    }
    public int doPost(String method, List<NameValuePair> params, NetworkListener.CryptoService listener){
        params.add(new BasicNameValuePair("user", encrypt(user.getBytes(), enckey)));
        params.add(new BasicNameValuePair("pass", encrypt(pass, enckey)));
        params.add(new BasicNameValuePair("method", method));

        return handlePost(method, params, listener);
    }
    protected abstract int handlePost(String method, List<NameValuePair> params, NetworkListener.CryptoService listener);

    private static byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000');
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }

    public static String encrypt(byte[] input, byte[] key){
        byte[] crypted = null;
        try{
            SecretKeySpec skey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input);
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return new String(Base64.getEncoder().encode(crypted));
    }

    public static String decrypt(byte[] input, byte[] key){
        System.out.println(input);
        byte[] output = null;
        try{
            SecretKeySpec skey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            String temp = new String(input);
            temp = temp.replace("\\", "");
            output = cipher.doFinal(Base64.getDecoder().decode(temp));
            temp = "";
        } catch(Exception e){
            System.out.println(e.toString());
        }
        return new String(output);
    }
}
