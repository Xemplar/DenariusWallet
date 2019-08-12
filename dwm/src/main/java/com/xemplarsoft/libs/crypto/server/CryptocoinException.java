package com.xemplarsoft.libs.crypto.server;

/**
 * Created by Rohan on 8/15/2017.
 */
public class CryptocoinException extends Exception {
    private int code;

    public CryptocoinException(int code, String message) {
        super(message);
        this.code = code;
    }
}