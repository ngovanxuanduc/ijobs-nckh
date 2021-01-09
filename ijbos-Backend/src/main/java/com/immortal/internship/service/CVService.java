package com.immortal.internship.service;

import com.immortal.internship.entity.CVEntity;
import com.immortal.internship.entity.StudentEntity;
import com.immortal.internship.payload.request.CVForm;
import com.immortal.internship.payload.response.StudentCVResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CVService {

    CVEntity getCVById(UUID id);

    CVEntity getCV();

    CVEntity getActiveCVById(UUID id);

    String acceptCV(UUID stId, String reId);

    String rejectCV(UUID stId, String reId);


    CVEntity createCV(CVForm cvForm);

    List<StudentCVResponse> getStudentList();

    String cancelCV(UUID id);

    String approveCV(UUID id);

    List<StudentEntity> getListStudentIntern();

    String updateCV(CVForm cvForm);
}
