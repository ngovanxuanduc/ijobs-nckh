package com.immortal.internship.payload.response;

import com.immortal.internship.model.BaseStatistical;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanyStatisticalResponse {
    private int numOfCompany;
    private List<BaseStatistical> details;
}
