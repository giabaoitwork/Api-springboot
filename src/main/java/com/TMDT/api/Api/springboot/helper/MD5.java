package com.TMDT.api.Api.springboot.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static String getHashMD5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String text, String hash) {
        return getHashMD5(text).equals(hash);
    }

    public static void main(String[] args) {
        System.out.println(getHashMD5("bao123"));
        System.out.println(verify("bao123", "b6c6cfe1a7ba5eac0f984f3ef97c8490"));
    }
}
