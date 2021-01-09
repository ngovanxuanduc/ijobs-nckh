package com.immortal.internship.service;

import com.immortal.internship.model.InternDetail;
import com.immortal.internship.payload.response.CompanyStatisticalResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface StatisticalService {
    List<InternDetail> getAllInternStudentByCompanyId(UUID comId);
    CompanyStatisticalResponse getStatistical();

}
