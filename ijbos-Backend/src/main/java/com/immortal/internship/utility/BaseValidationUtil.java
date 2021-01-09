package com.immortal.internship.utility;

public class BaseValidationUtil {
    public static boolean isEmptyString(String target){
        return target == null || target.length() == 0;
    }
}
