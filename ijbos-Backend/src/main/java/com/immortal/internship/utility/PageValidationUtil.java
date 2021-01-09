package com.immortal.internship.utility;

import java.util.Optional;

public class PageValidationUtil extends BaseValidationUtil{
    public static boolean isValidatedPageSize(Integer pageSize){
        return Optional.ofNullable(pageSize)
                .filter(x -> x>0)
                .isPresent();
    }
    public static boolean isValidatedPageNumber(Integer pageNumber){
        return Optional.ofNullable(pageNumber)
                .filter(x -> x>0)
                .isPresent();
    }
    public static boolean isValidatedSortType(String sortType){
        return  Optional.ofNullable(sortType).filter(x -> !isEmptyString(x)).isPresent()
                && ( "asc".equals(sortType) || "desc".equals(sortType));
    }
}
