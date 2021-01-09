package com.immortal.internship.constant;

public class RecruitmentConstant {

    public class Status{
        /**
         * status:
         * 	-2 : sẽ xoa tin nay khoi db | khong ai co the thay no
         * 	-1 : Xóa nhưng vãn lưu trên DB
         * 	 0 : chưa phê duyệt | chưa hết hạn
         * 	 1 : chưa phê duyệt | đã hết hạn
         * 	 2 : đã phê duyệt | chưa hết hạn
         * 	 3 : đã phê duyệt | hết hạn
         * */
        public static final int DELETE_FOREVER = -2;
        public static final int DELETE = -1;
        public static final int NOT_ACTIVE_NOT_EXPIRED = 0;
        public static final int NOT_ACTIVE_EXPIRED = 1;
        public static final int ACTIVE_NOT_EXPIRED = 2;
        public static final int ACTIVE_EXPIRED = 3;
    }

    public class Type{
        public static final int CREATED_BY_COMPANY = 1;
        public static final int CREATED_BY_SCHOOL = 2;
    }
}
