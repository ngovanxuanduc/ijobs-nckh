package com.immortal.internship.constant;

import java.util.UUID;

public class AccountActiveConstants {
    /**
     * Active:
     * 	 0 : tai khoan khong con ton tai
     * 	 1 : tai khoan dang bi ban
     * 	 2 : tai khoan chua hoan thanh profile
     * 	 3 : tai khoan binh thuong
     * */
    public static final int DOES_NOT_EXIST = 0;
    public static final int BANED = 1;
    public static final int UNFINISHED_PROFILE = 2;
    public static final int NORMAL_ACCOUNT = 3;
}
