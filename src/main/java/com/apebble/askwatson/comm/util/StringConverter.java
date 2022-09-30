package com.apebble.askwatson.comm.util;

public class StringConverter {

    public static String strToUniCode(String plainText) {
        plainText = plainText.replace(" ", "");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < plainText.length(); i++) {
            sb.append("%" + Integer.toHexString(plainText.charAt(i)).toUpperCase());
        }
        return sb.toString();
    }
}
