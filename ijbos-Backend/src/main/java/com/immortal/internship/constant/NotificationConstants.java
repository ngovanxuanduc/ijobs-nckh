package com.immortal.internship.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class NotificationConstants {
    /**
     * status:
     * 	 1 : Chua xem
     * 	 2 : Da xem
     * */
    public static final int NOT_SEEN = 1;
    public static final int SEEN = 2;

    /**
     * 1: company
     * 2: student
     * 4: School
     * */

    public static GroupID[] groupIDS = new GroupID[] {new GroupID(1,"Company"),new GroupID(2,"Student"), new GroupID(4,"School")};

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupID{
        private int id;
        private String description;
    }
}
