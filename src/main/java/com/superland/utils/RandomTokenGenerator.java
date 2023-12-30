package com.superland.utils;
import java.security.SecureRandom;

public class RandomTokenGenerator {
    public static void main(String[] args) {
        int desiredLength = 10; // Ganti dengan panjang yang Anda inginkan
        String randomToken = generateRandomToken(desiredLength);
        System.out.println("Random Token: " + randomToken);
    }

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static SecureRandom random = new SecureRandom();

    public static String generateRandomToken(int length) {
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return token.toString();
    }
}
