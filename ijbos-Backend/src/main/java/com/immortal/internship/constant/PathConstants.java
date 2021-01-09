package com.immortal.internship.constant;

public class PathConstants {
    /**
     * Base Path
     */
    public static final String AUTH_PATH = "/api/auth";

    public static final String AFTER_LOGIN_PATH = "/api/afterLogin";

    public static final String RECRUITMENT_PATH = "/api/recruitments";

    public static final String CV_PATH = "/api/cv";

    public static final String STUDENT = "/api/student";

    public static final String ACCOUNT_PATH = "/api/accounts";

    public static final String NOTIFICATION_PATH = "/api/notification";

    public static final String SKILLS = "/api/skills";

    public static final String STATISTICAL = "/api/statistical/";

    public static final String FORWARD_SLASH = "/";

    public static final String SORT_BY_PAGE = "/sbp";

    public static final String DISCUSS = "/api/discuss";
    /**
     * CRUD path
     * */
    public static final String ALL = "/all";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String APPROVE = "/approve";
    public static final String REPLY = "/reply";

    /**
     * Path Parameter
     */
    public static final String ID = "/{id}";

    /**
     * Home path
     */
    public static final String LOGIN_WEB = "/loginWeb";
    public static final String LOGIN_MOBILE = "/loginMobile";
    public static final String CHANGE_PASS = "/changePassword";
    public static final String LOGOUT_WEB = "/logoutWeb";
    public static final String LOGOUT_MOBILE = "/logoutMobile";
    public static final String PUBLIC = "/public";



    /**
     * Recruitment Path
     */
    public static final String GET_RECRUITMENT_BY_CREATE_BY = "/getRecruitmentByCreateBy/{id}";
    public static final String GET_RECRUITMENT_BY_ID_PUBLIC = "/getRecruitmentIdPublic/{id}";
    public static final String LOGIN = "login";


    /**
     * Recruitment Request Param
     */
    public static final String PAGE = "page";
    public static final String PAGE_SIZE = "pageSize";
    public static final String SORT_BY= "sortBy";
    public static final String TYPE_SORT= "typeSort";

    public static final String DEFAULT_PAGE = "1";
    public static final String DEFAULT_PAGE_SIZE= "10";
    public static final String DEFAULT_SORT_BY= "createdAt";
    public static final String DEFAULT_TYPE_SORT= "desc";

    /**
     * Account Path
     */
    public static final String UPDATE_STUDENT_ACCOUNT = "/updateStudentAcc/{id}";
    public static final String UPDATE_TEACHER_ACCOUNT = "/updateTeacherAcc/{id}";
    public static final String UPDATE_COMPANY_ACCOUNT = "/updateCompanyAcc/{id}";
    public static final String CREATE_ACCOUNT = "/create";
    public static final String CREATE_STUDENT_ACCOUNT = "/createStudentAcc";
    public static final String CREATE_COMPANY_ACCOUNT = "/createCompanyAcc";
    public static final String CREATE_LECTURE_ACCOUNT = "/createLectureAcc";
    public static final String CREATE_ADMIN_ACCOUNT = "/createAdminAcc";
    public static final String GET_STUDENT_PROFILE = "/getStudentProfile/{id}";
    public static final String GET_STUDENTS_PROFILE = "/getAllStudentProfile";
    public static final String GET_COMPANY_PROFILE = "/getCompanyProfile/{id}";
    public static final String GET_COMPANYS_PROFILE = "/getAllCompanyProfile";
    public static final String GET_TEACHER_PROFILE = "/getTeacherProfile/{id}";
    public static final String GET_TEACHERS_PROFILE = "/getAllTeacherProfile";
    public static final String DELETE_ACCOUNT = "/deleteAcc/{id}";
    /**
     * Account Request Param
     */

    public static final String ACCOUNT_USERNAME = "username";
    
    public static final String CREATE_NOTIFICATION = "/create";
    public static final String GET_GROUP_NOTIFICATION = "group";
    public static final String DEFAULT_ACCOUNT_SORT_BY= "user_name";
    public static final String DEFAULT_STUDENT_PROFILE_SORT_BY= "first_name";
    public static final String DEFAULT_COMPANY_PROFILE_SORT_BY= "name";
    public static final String DEFAULT_TEACHER_PROFILE_SORT_BY= "last_name";
    public static final Boolean WEB_TOKEN = true;

    /**
     * Student Path
     */

    public static final String APPLY = "/apply";
    public static final String BOOK_MARK = "/bookmark";


    /**
     * CV path
     */

    public static final String GET_ACTIVE_CV_BY_STUDENT_ID = "/getActiveCVById/{id}";
    public static final String GET_CV_BY_STUDENT_ID = "/getCVById/{id}";
    public static final String ACCEPT_CV = "/acceptCV";
    public static final String REJECT_CV = "/rejectCV";
    public static final String APPROVE_CV = "/approveCV/{id}";
    public static final String CANCEL_CV = "/cancelCV/{id}";
    public static final String CREATE_CV = "/createCV";
    public static final String GET_STUDENT_LIST = "/getStudentList";
    public static final String GET_SUGGEST_CV_LIST = "/getSuggestCVList/{recId}";
    public static final String GET_CV = "/getCV";

    /**
     * CV Request Param
     */
    public static final String STUDENT_ID = "stId";
    public static final String RECRUITMENT_ID = "reId";


    /**
     * Statistical path
     */
    public static final String GET_ALL_STUDENT_INTERN_BY_COMPANY_ID = "/getAllInternStudentByCompanyId/{comId}";
    public static final String GET_FULL_DISCUSS_BY_ID = "/getFullDiscussById/{disId}";
    public static final String GET_STATISTICAL = "/getStatistical";
}
