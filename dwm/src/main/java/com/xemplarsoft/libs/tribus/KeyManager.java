package com.xemplarsoft.libs.tribus;

import com.xemplarsoft.libs.util.Base58;
import com.xemplarsoft.libs.util.Ripemd160;
import com.xemplarsoft.libs.util.Sha256;


public final class KeyManager {
    public static String convertPubToAddress(byte[] pub){
        byte[] data = Ripemd160.getHash(Sha256.getHash(pub));
        byte[] dat = new byte[data.length + 1];
        System.arraycopy(data, 0, dat, 1, data.length);
        dat[0] = (byte)0x1E;

        byte[] checksum = Sha256.getHash(Sha256.getHash(dat));
        checksum = slice(checksum, 4);

        byte[] fin = new byte[dat.length + 4];
        System.arraycopy(dat, 0, fin, 0, dat.length);
        System.arraycopy(checksum, 0, fin, dat.length, 4);

        return Base58.encode(fin);
    }

    public static String convertPubToAddress(String pub){
        return convertPubToAddress(hex2bytes(pub));
    }

    public static String convertPrivToWIF(byte[] priv){
        byte[] key = new byte[priv.length + 2];
        System.arraycopy(priv, 0, key, 1, priv.length);
        key[0] = (byte)0x9E;
        key[key.length - 1] = (byte)0x01;

        byte[] checksum = Sha256.getHash(Sha256.getHash(key));
        checksum = slice(checksum, 4);

        byte[] fin = new byte[key.length + 4];
        System.arraycopy(key, 0, fin, 0, key.length);
        System.arraycopy(checksum, 0, fin, key.length, 4);

        return Base58.encode(fin);
    }

    public static byte[] slice(byte[] a, int leng) {
        byte[] ret = new byte[leng];
        for (int i = 0; i < leng; i++) {
            ret[i] = a[i];
        }
        return ret;
    }

    public static String convertPrivToWIF(String priv){
        return convertPrivToWIF(hex2bytes(priv));
    }

    private static final String EVEN = "02";
    private static final String ODD = "03";

    public static String compressPublicKey(String toCompress) {
        if (Integer.parseInt(toCompress.substring(128, 130), 16) % 2 == 0)
            return  EVEN + toCompress.substring(2, 66);
        return ODD + toCompress.substring(2, 66);
    }

    private static byte[] hex2bytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
