package com.immortal.internship.service.ServiceImpl;

import com.immortal.internship.model.InternDetail;
import com.immortal.internship.payload.response.CompanyStatisticalResponse;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatisticalServiceImpl implements StatisticalService {
    @Autowired
    DBContext dbContext;

    @Override
    public List<InternDetail> getAllInternStudentByCompanyId(UUID comId) {
        return Optional.ofNullable(dbContext.customRepository.getAllInternStudentByCompanyId(comId))
                .orElseGet(ArrayList::new);
    }

    @Override
    public CompanyStatisticalResponse getStatistical() {
        return Optional.ofNullable(dbContext.customRepository.getStatistical())
                .orElseGet(null);
    }




}
