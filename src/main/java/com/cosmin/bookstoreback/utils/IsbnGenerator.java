package com.cosmin.bookstoreback.utils;

import org.springframework.stereotype.Component;

@Component
public class IsbnGenerator implements NumberGenerator {

    public static String getRandomInteger(int minimum, int maximum) {
        return String.valueOf(((int) (Math.random() * (maximum - minimum + 1) + minimum)));
    }

    @Override
    public String generateNumber() {
        return Consts.ISBN_PREFIX + "-" + getRandomInteger(0, 9) + "-" + getRandomInteger(10000, 99999);
    }

}
