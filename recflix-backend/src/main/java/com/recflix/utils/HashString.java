package com.recflix.utils;

import java.security.MessageDigest;

/**
 * HashString
 */
public class HashString {

    public static String hashTheString(String _string) {
        StringBuffer hexString = new StringBuffer();
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(_string.getBytes("UTF-8"));

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return hexString.toString();
    }
}
