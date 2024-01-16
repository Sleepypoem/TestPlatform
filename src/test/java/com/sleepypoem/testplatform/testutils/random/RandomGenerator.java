package com.sleepypoem.testplatform.testutils.random;

import java.util.Random;

public class RandomGenerator {

    private RandomGenerator() {
    }

    public static int getRandomInt(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }


    public static double getRandomDouble(double min, double max) {
        return min + Math.random() * (max - min);
    }

    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) getRandomInt('a', 'z'));
        }
        return sb.toString();

    }

    public static Long getRandomLong(long min, long max) {
        Random r = new Random();
        return r.nextLong() % (max - min + 1) + min;
    }
}
