package com.immortal.internship.controller;


import com.immortal.internship.aop.annotation.UUID;
import com.immortal.internship.constant.PathConstants;
import com.immortal.internship.constant.RoleConstants;
import com.immortal.internship.payload.request.*;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.payload.response.CompanyProfileResponse;
import com.immortal.internship.payload.response.StudentProfileResponse;
import com.immortal.internship.payload.response.TeacherProfileResponse;
import com.immortal.internship.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(PathConstants.ACCOUNT_PATH)
@Validated
public class AccountController {
    @Autowired
    AccountService accountService;

    @Operation(summary = "Get All Account", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    @GetMapping(PathConstants.FORWARD_SLASH)
    public BaseResponse getAllAccounts() {
        return BaseResponse.ok(accountService.getAllAccount()).build();
    }


    @Operation(summary = "Get Accounts Sort By Page", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    @GetMapping(PathConstants.SORT_BY_PAGE)
    public BaseResponse getAccountSortByPage(
            @RequestParam(value = PathConstants.PAGE_SIZE
                    , required = false
                    , defaultValue = PathConstants.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(value = PathConstants.PAGE
                    , defaultValue = PathConstants.DEFAULT_PAGE) Integer page,
            @RequestParam(value = PathConstants.SORT_BY
                    , defaultValue = PathConstants.DEFAULT_ACCOUNT_SORT_BY) String sortBy,
            @RequestParam(value = PathConstants.TYPE_SORT
                    , defaultValue = PathConstants.DEFAULT_TYPE_SORT) String typeSort) {
        return BaseResponse.ok(accountService.getAccountSortByPage(page, pageSize, sortBy, typeSort)).build();
    }

    @Operation(summary = "Update Student Account", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    @PutMapping(value = PathConstants.UPDATE_STUDENT_ACCOUNT)
    public BaseResponse updateStudentAccount(@Valid @RequestBody StudentProfileUpdate studentProfile
            , @PathVariable(name = "id") @UUID String id) {
        return BaseResponse.ok(accountService.updateStudentAccount(studentProfile, java.util.UUID.fromString(id))).build();
    }

    @Operation(summary = "Update Teacher Account", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    @PutMapping(value = PathConstants.UPDATE_TEACHER_ACCOUNT)
    public BaseResponse updateTeacherAccount(@Valid @RequestBody TeacherProfileUpdate teacherProfile
            , @PathVariable(name = "id") @UUID String id) {
        return BaseResponse.ok(accountService.updateTeacherAccount(teacherProfile, java.util.UUID.fromString(id))).build();
    }


    @Operation(summary = "Update Company Account", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    @PutMapping(value = PathConstants.UPDATE_COMPANY_ACCOUNT)
    public BaseResponse updateCompanyAccount(@Valid @RequestBody CompanyProfileUpdate companyProfile
            , @PathVariable(name = "id") @UUID String id) {
        return BaseResponse.ok(accountService.updateCompanyAccount(companyProfile, java.util.UUID.fromString(id))).build();
    }

    @Operation(summary = "Delete Account", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    @DeleteMapping(value = PathConstants.DELETE_ACCOUNT)
    public BaseResponse deleteAccount(@PathVariable(name = "id") @UUID String id) {
        return BaseResponse.ok(accountService.deleteAccount(java.util.UUID.fromString(id))).build();
    }

    @Operation(summary = "Create Company Account", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    @PostMapping(PathConstants.CREATE_COMPANY_ACCOUNT)
    public BaseResponse createCompanyAccount(@Valid @RequestBody CompanyAccountFormCreate comAccount) {
        return BaseResponse.ok(accountService.createCompanyAccount(comAccount)).build();
//        return BaseResponse.ok(new CompanyEntity()).build();

    }

    @Operation(summary = "Create Lecture Account", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    @PostMapping(PathConstants.CREATE_LECTURE_ACCOUNT)
    public BaseResponse createLectureAccount(@Valid @RequestBody SchoolAccountFormCreate schAccount) {
        return BaseResponse.ok(accountService.createLectureAccount(schAccount)).build();
    }

    @Operation(summary = "Create Admin Account", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    @PostMapping(PathConstants.CREATE_ADMIN_ACCOUNT)
    public BaseResponse createAdminAccount(@Valid @RequestBody SchoolAccountFormCreate schAccount) {
        return BaseResponse.ok(accountService.createAdminAccount(schAccount)).build();
    }

    @Operation(summary = "Create Student Account", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    @PostMapping(PathConstants.CREATE_STUDENT_ACCOUNT)
    public BaseResponse createStudentAccount(@Valid @RequestBody StudentAccountFormCreate stAccount) {
        return BaseResponse.ok(accountService.createStudentAccount(stAccount)).build();

    }

    @Operation(summary = "Get Account By ID", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(PathConstants.ID)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    public BaseResponse getInfAccountById(@PathVariable(name = "id") @UUID String id) {
        return BaseResponse.ok(accountService.getAccountById(java.util.UUID.fromString(id))).build();
    }

    @Operation(summary = "Get Teacher Profile By ID", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(PathConstants.GET_TEACHER_PROFILE)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    public BaseResponse getTeacherProfileById(@PathVariable(name = "id") @UUID String id) {
        return BaseResponse.ok(accountService.getTeacherProfile(java.util.UUID.fromString(id))).build();
    }


    @Operation(summary = "Get Student Profile By ID", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(PathConstants.GET_STUDENT_PROFILE)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    public BaseResponse getStudentProfileById(@PathVariable(name = "id") @UUID String id) {
        return BaseResponse.ok(accountService.getStudentProfile(java.util.UUID.fromString(id))).build();
    }

    @Operation(summary = "Get Company Profile By ID", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(PathConstants.GET_COMPANY_PROFILE)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    public BaseResponse getCompanyProfileById(@PathVariable(name = "id") @UUID String id) {
        return BaseResponse.ok(accountService.getCompanyProfile(java.util.UUID.fromString(id))).build();
    }


    @Operation(summary = "Get Student Profile Sort By Page", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(PathConstants.GET_STUDENTS_PROFILE)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    public BaseResponse getStudentProfileSortByPage(
            @RequestParam(value = PathConstants.PAGE_SIZE
                    , required = false
                    , defaultValue = PathConstants.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(value = PathConstants.PAGE
                    , defaultValue = PathConstants.DEFAULT_PAGE) Integer page,
            @RequestParam(value = PathConstants.SORT_BY
                    , defaultValue = PathConstants.DEFAULT_STUDENT_PROFILE_SORT_BY) String sortBy,
            @RequestParam(value = PathConstants.TYPE_SORT
                    , defaultValue = PathConstants.DEFAULT_TYPE_SORT) String typeSort) {
        return BaseResponse.ok(accountService.getStudentProfileByPage(page, pageSize, sortBy, typeSort)).build();
    }

    @Operation(summary = "Get Company Profile Sort By Page", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(PathConstants.GET_COMPANYS_PROFILE)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    public BaseResponse getCompanyProfileSortByPage(
            @RequestParam(value = PathConstants.PAGE_SIZE
                    , required = false
                    , defaultValue = PathConstants.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(value = PathConstants.PAGE
                    , defaultValue = PathConstants.DEFAULT_PAGE) Integer page,
            @RequestParam(value = PathConstants.SORT_BY
                    , defaultValue = PathConstants.DEFAULT_COMPANY_PROFILE_SORT_BY) String sortBy,
            @RequestParam(value = PathConstants.TYPE_SORT
                    , defaultValue = PathConstants.DEFAULT_TYPE_SORT) String typeSort) {
        return BaseResponse.ok(accountService.getCompanyProfileByPage(page, pageSize, sortBy, typeSort)).build();
    }


    @Operation(summary = "Get Teacher Profile Sort By Page", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(PathConstants.GET_TEACHERS_PROFILE)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "')")
    public BaseResponse getTeacherProfileSortByPage(
            @RequestParam(value = PathConstants.PAGE_SIZE
                    , required = false
                    , defaultValue = PathConstants.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(value = PathConstants.PAGE
                    , defaultValue = PathConstants.DEFAULT_PAGE) Integer page,
            @RequestParam(value = PathConstants.SORT_BY
                    , defaultValue = PathConstants.DEFAULT_TEACHER_PROFILE_SORT_BY) String sortBy,
            @RequestParam(value = PathConstants.TYPE_SORT
                    , defaultValue = PathConstants.DEFAULT_TYPE_SORT) String typeSort) {
        return BaseResponse.ok(accountService.getTeacherProfileByPage(page, pageSize, sortBy, typeSort)).build();
    }

}
