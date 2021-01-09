package com.immortal.internship.constant;

public class MessageConstants {
    /**
     * Mess for system
     */
    public class ForSystem {
        public static final String INVALID_CURRENT_USER = "Invalid current user";
        public static final String INVALID_PARAM = "Invalid Param";
        public static final String SUCCESS = "Success";
        public static final String FAIL = "Something was wrong";
        public static final String FILE_NOT_FOUND = "File Not Found";
        public static final String CHANGE_PASS_SUCCESS = "Change Password Success";
        public static final String FORBIDDEN = "Forbidden";
        public static final String ENTITY_NOT_FOUND = "Not Found";
        public static final String NOT_COMPLETE_PROFILE = "Profile not complete !!!";
        public static final String DISCUSS_CLOSED = "Discuss Closed !!!";
        public static final String SAME_USER = "Same User !!!";
    }

    /**
     * Message for user
     */
    public class ForUser {
        public static final String USER_NOT_FOUND_WITH_USERNAME_OR_PASSWORD = "We were not able to find a user with that username and password";
        public static final String PAGE_SIZE_ERROR = "Page size must not be less than one!";
        public static final String PAGE_NUMBER_ERROR = "Page number must not be less than one!";
        public static final String SORT_BY_ERROR = "Invalid sort by ! ";
        public static final String TYPE_SORT_ERROR = "Invalid type sort ! ";
        public static final String INCORRECT_PASSWORD = " Incorrect Password";
        public static final String INVALID_NAME = "Invalid name";
        public static final String INVALID_PASSWORD = "Invalid Password";
        public static final String ACCESS_DENIED = "Access Denied !!!";
        public static final String INVALID_PARAMS = "Valid Params !!!";
        public static final String SKILL_NOT_EXIST = "Skill Not Found !!!";
        public static final String GROUP_NOT_EXIST = "Group Not Found !!!";
        public static final String RECRUITMENT_NOT_EXIST = "Recruitment Not Found !!!";
        public static final String DELETE_RECRUITMENT = "Delete Recruitment Success";
        public static final String DELETE_ACCOUNT_SUCCESS = "Delete Account Success";
        public static final String APPROVE_RECRUITMENT = "Approve Recruitment Success";
        public static final String EXISTED_STUDENT_CODE = "Student code is exist !!!";
        public static final String EXISTED_USERNAME = "User name is exist !!!";
        public static final String ROLE_NOT_EXIST = "Role Not Found !!!";
        public static final String ACCOUNT_NOT_FOUND = "Account Not Found !!!";
        public static final String LOGOUT_SUCCESS = "Logout Success!";
        public static final String INVALID_UUID = "Invalid UUID!";
        public static final String STUDENT_OUT_OF_ROLE = "Should Only Role Student !!!";
        public static final String COMPANY_OUT_OF_ROLE = "Should Only Role Company !!!";
        public static final String TEACHER_OUT_OF_ROLE = "Should Only Role Teacher !!!";
        public static final String ADMIN_OUT_OF_ROLE = "Should Only Role Admin !!!";
        public static final String NOT_COMPLETE_PROFILE = "You should complete your profile !!!";
        public static final String APPLY_SUCCESS = "Apply Success !!!";
        public static final String BOOKMARK_SUCCESS = "Bookmark Success !!!";
        public static final String UN_APPLY_SUCCESS = " UnApply Success !!!";
        public static final String UN_BOOKMARK_SUCCESS = "UnBookmark Success !!!";
        public static final String UPDATE_ACCOUNT_SUCCESS = "Update Account Success !!!";
        public static final String EXISTED_CV = "This account have a CV !!!";



        public static final String CV_NOT_FOUND = "CV Not Found !!!";
        public static final String ACCEPT_CV_SUCCESS = "Accept CV Success !!!";
        public static final String REJECT_CV_SUCCESS = "Reject CV Success!!!";
        public static final String APPROVED_CV_SUCCESS = "Approved CV Success!!!";

        public static final String CONTENT_NOT_NULL = "Content Not Null !!!";
        public static final String SAVE_DISCUSS_CONTENT_OK = "Save Discuss Content Ok !!!";
        public static final String DISCUSS_CLOSED = "Discuss Closed !!!";
        public static final String DISCUSS_NOT_FOUND = "Discuss not found !!!";

    }

    public class ResultCode {
        public static final int FAIL = 400;
        public static final int SUCCESS = 200;
        public static final int UNAUTHORIZED = 401;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int METHOD_NOT_ALLOWED = 405;

        /*-----------------------------------------------------*/
        public static final int BAD_REQUEST_PARAMS = 1001;
        public static final int BAD_REQUEST_VALID_PARAMS = 1002;
        public static final int ENTITY_NOT_FOUND = 1003;

        /**
         * loi lien quan den USER ma code bat dau tu 11**
         */
        public static final int INVALID_NAME = 1101;
        public static final int INVALID_PASSWORD = 1102;
        public static final int INVALID_OLD_PASSWORD = 1003;
        public static final int INVALID_CURRENT_USER = 1104;
        public static final int NOT_PERMISSION = 1105;
        public static final int BAD_CREDENTIALS = 1106;
        public static final int PROFILE_NOT_COMPLETE = 1107;
        public static final int EXISTED_USER = 1108;
    }

}


