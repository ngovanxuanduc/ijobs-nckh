package com.immortal.internship.service;

import com.immortal.internship.entity.AccountEntity;
import com.immortal.internship.entity.CompanyEntity;
import com.immortal.internship.entity.StudentEntity;
import com.immortal.internship.entity.TeacherEntity;
import com.immortal.internship.payload.request.*;
import com.immortal.internship.payload.response.CompanyProfileResponse;
import com.immortal.internship.payload.response.PageResponse;
import com.immortal.internship.payload.response.StudentProfileResponse;
import com.immortal.internship.payload.response.TeacherProfileResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface AccountService {

    Optional<List<AccountEntity>> getAllAccount();

    AccountEntity createAccount(BaseAccountForm infAcc);

    StudentEntity createStudentAccount(StudentAccountFormCreate infStAcc);

    TeacherEntity createLectureAccount(SchoolAccountFormCreate infSchAcc);

    TeacherEntity createAdminAccount(SchoolAccountFormCreate infSchAcc);

    CompanyEntity createCompanyAccount(CompanyAccountFormCreate infComAcc);

    List<AccountEntity> getAccountSortByPage(Integer pageNumber
            , Integer pageSize, String sortBy, String typeSort);

    String updateStudentAccount(StudentProfileUpdate infAcc, UUID id);

    String updateTeacherAccount(TeacherProfileUpdate infAcc, UUID id);

    String updateCompanyAccount(CompanyProfileUpdate infAcc, UUID id);

    String deleteAccount(UUID id);

    AccountEntity getAccountById(UUID id);

    StudentProfileResponse getStudentProfile(UUID id);

    CompanyProfileResponse getCompanyProfile(UUID id);

    TeacherProfileResponse getTeacherProfile(UUID id);

    PageResponse getStudentProfileByPage(Integer pageNumber, Integer pageSize, String sortBy, String typeSort);

    PageResponse getCompanyProfileByPage(Integer pageNumber, Integer pageSize, String sortBy, String typeSort);

    PageResponse getTeacherProfileByPage(Integer pageNumber, Integer pageSize, String sortBy, String typeSort);


}
