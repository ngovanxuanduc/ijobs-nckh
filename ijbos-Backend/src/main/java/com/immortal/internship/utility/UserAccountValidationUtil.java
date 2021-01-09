package com.immortal.internship.utility;


import java.util.Optional;

public class UserAccountValidationUtil extends BaseValidationUtil{

    private static String USERNAME_PATTERN = "[A-Za-z0-9_]{3,64}";
    private static String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,50}$";


    public static boolean isValidateUserName(String userName){
        return Optional.ofNullable(userName)
                .filter(x -> !isEmptyString(x))
                .filter(x -> x.matches(USERNAME_PATTERN))
                .isPresent();
    }

    public static boolean isValidatePassword(String password){
        return Optional.ofNullable(password)
                .filter(x -> !isEmptyString(x))
                .filter(x -> x.matches(PASSWORD_PATTERN))
                .isPresent();
    }

}
