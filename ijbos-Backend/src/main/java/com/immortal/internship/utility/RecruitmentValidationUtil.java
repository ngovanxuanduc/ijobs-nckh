package com.immortal.internship.utility;

import com.immortal.internship.constant.MessageConstants;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RecruitmentValidationUtil extends BaseValidationUtil{

    private static final String[] recruitmentSortByParam = {"id","title","active","description","startTime","endTime"
            ,"campaignName","location","location_description","createdBy","approveBy","is_delete","skills","createdAt"};

    public static boolean isValidatedSortBy(String sortBy){
        return Optional.ofNullable(sortBy).filter(x -> !isEmptyString(x)).isPresent()
                && Arrays.stream(recruitmentSortByParam).anyMatch(x -> sortBy.equals(x));
    }


}
