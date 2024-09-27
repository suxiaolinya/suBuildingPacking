package cn.suxiaolin.subuildingpacking.util;

import java.util.Random;

public class RandomID {

    private static final String validChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_/";
    private static final Random random = new Random();

    private static char getRandomChar() {
        return validChars.charAt(random.nextInt(64));
    }

    public static String generateRandomID() {
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            codeBuilder.append(getRandomChar());
        }
        return codeBuilder.toString();
    }
}
