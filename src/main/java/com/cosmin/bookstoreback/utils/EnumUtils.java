package com.cosmin.bookstoreback.utils;

public class EnumUtils {

    public static boolean isPresent(Enum[] enumValues, String value) {
        for (Enum element : enumValues) {
            if (element.toString().equals(value))
                return true;
        }
        return false;
    }

}
