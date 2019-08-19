package com.xemplarsoft.libs.util;

public final class StringUtils {
    public static String invertString(String in){
        String ret = "";
        for(int i = in.length(); i > 0; i--){
            ret += in.charAt(i - 1);
        }

        return ret;
    }
}
