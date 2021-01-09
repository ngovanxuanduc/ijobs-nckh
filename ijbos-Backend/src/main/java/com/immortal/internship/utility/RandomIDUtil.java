package com.immortal.internship.utility;

import java.util.Random;

public class RandomIDUtil {

    private static final int leftLimit = 48; // numeral '0'
    private static final int  rightLimit = 122; // letter 'z'
    private static final int targetStringLength = 15;

    public static String getGeneratedString() {
        return new Random().ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


    public static String getGeneratedString(int length) {
        return new Random().ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
