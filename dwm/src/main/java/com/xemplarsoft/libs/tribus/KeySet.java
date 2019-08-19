package com.xemplarsoft.libs.tribus;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECPoint;

public class KeySet {
    public final String pubkey, privkey;

    public KeySet(String privkey, String pubkey){
        this.privkey = privkey;
        this.pubkey = pubkey;
    }

    public static KeySet generateAddress(){
        String pubkey, privkey;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
            keyGen.initialize(ecSpec);

            KeyPair kp = keyGen.generateKeyPair();
            PublicKey pub = kp.getPublic();
            PrivateKey pvt = kp.getPrivate();

            ECPrivateKey epvt = (ECPrivateKey)pvt;
            String sepvt = adjustTo64(epvt.getS().toString(16)).toUpperCase();

            ECPublicKey epub = (ECPublicKey)pub;
            ECPoint pt = epub.getW();
            String sx = adjustTo64(pt.getAffineX().toString(16)).toUpperCase();
            String sy = adjustTo64(pt.getAffineY().toString(16)).toUpperCase();

            pubkey = "1E" + sx + sy;
            privkey = sepvt;
        } catch (Exception e){
            e.printStackTrace();
            pubkey = privkey = null;
        }

        return new KeySet(privkey, pubkey);
    }

    public String getAddress() {
        return KeyManager.convertPubToAddress(pubkey);
    }

    public String getPrivkey() {
        return privkey;
    }

    public String getPubkey() {
        return pubkey;
    }

    public String getWIF(){
        return KeyManager.convertPrivToWIF(privkey);
    }

    private static String adjustTo64(String s) {
        switch(s.length()) {
            case 62: return "00" + s;
            case 63: return "0" + s;
            case 64: return s;
            default:
                throw new IllegalArgumentException("not a valid key: " + s);
        }
    }
}
