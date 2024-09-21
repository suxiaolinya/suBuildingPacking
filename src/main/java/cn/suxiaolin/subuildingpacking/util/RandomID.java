package cn.suxiaolin.subuildingpacking.util;

import java.util.Random;

public class RandomID {
    private static char getRandomChar() {
        Random random = new Random();
        int type = random.nextInt(3); // 0: 数字, 1: 小写字母, 2: 大写字母
        switch (type) {
            case 0:
                return (char) ('0' + random.nextInt(10));
            case 1:
                return (char) ('a' + random.nextInt(26));
            case 2:
                return (char) ('A' + random.nextInt(26));
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public static String generateRandomID() {
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            char randomChar = getRandomChar();
            codeBuilder.append(randomChar);
        }

        return codeBuilder.toString();
    }
}
