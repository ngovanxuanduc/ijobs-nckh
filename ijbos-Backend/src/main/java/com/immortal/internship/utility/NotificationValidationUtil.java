package com.immortal.internship.utility;

import java.util.Arrays;

public class NotificationValidationUtil extends BaseValidationUtil{
    public static final int[] GROUP = {1,2,4, 1|2, 1|4, 2|4, 1|2|4};

    public static boolean isValidateGroupID(int groupID ){
        return Arrays.stream(GROUP).anyMatch(x-> x == groupID);
    }
}
