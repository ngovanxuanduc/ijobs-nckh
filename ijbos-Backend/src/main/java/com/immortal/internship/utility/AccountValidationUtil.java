package com.immortal.internship.utility;

import java.util.Arrays;
import java.util.Optional;

public class AccountValidationUtil extends BaseValidationUtil{
    private static final String[] accountSortByParam = {"id","user_name","password",
            "active","create_by","updated_by","roles"};
    private static final String[] studentProfileSortByParam = {"account_id","student_code","school_year",
            "first_name","last_name","birth_date","gender","email","phone_number","address",
            "department","klass"};
    private static final String[] companyProfileSortByParam = {"account_id","name","company_info",
            "location","phone_number","email","working_date","country","over_view"};
    private static final String[] teacherProfileSortByParam = {"account_id","first_name","last_name",
            "phone_number","email","gender","address","department"};

    public static boolean isValidatedSortBy(String[] typeSort, String sortBy){
        return Optional.ofNullable(sortBy).filter(x -> !isEmptyString(x)).isPresent()
                && Arrays.stream(typeSort).anyMatch(x -> sortBy.equals(x));
    }

    public static boolean isValidatedAccountSortBy(String sortBy){
        return isValidatedSortBy(accountSortByParam, sortBy);
    }
    public static boolean isValidatedStudentProfileSortBy(String sortBy){
        return isValidatedSortBy(studentProfileSortByParam, sortBy);
    }
    public static boolean isValidatedCompanyProfileSortBy(String sortBy){
        return isValidatedSortBy(companyProfileSortByParam, sortBy);
    }
    public static boolean isValidatedTeacherProfileSortBy(String sortBy){
        return isValidatedSortBy(teacherProfileSortByParam, sortBy);
    }
}
