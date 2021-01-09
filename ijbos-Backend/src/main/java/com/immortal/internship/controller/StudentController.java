package com.immortal.internship.controller;

import com.immortal.internship.constant.PathConstants;
import com.immortal.internship.constant.RoleConstants;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathConstants.STUDENT)
public class StudentController {

    @Autowired
    private StudentService studentService;


    @PostMapping(PathConstants.APPLY+PathConstants.ID)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_STUDENT + "') ")
    @Operation(summary = "Apply Campaign Recruitment", security = @SecurityRequirement(name = "bearerAuth"))
    public BaseResponse applyCampaignRecruitment(@PathVariable(name = "id") String id ){
        return BaseResponse.ok(studentService.applyCampaignRecruitment(id)).build();
    }

    @PostMapping(PathConstants.BOOK_MARK+PathConstants.ID)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_STUDENT + "') ")
    @Operation(summary = "BookMark Campaign Recruitment", security = @SecurityRequirement(name = "bearerAuth"))
    public BaseResponse bookMarkCampaignRecruitment(@PathVariable(name = "id") String id ){
        return BaseResponse.ok(studentService.bookMarkCampaignRecruitment(id)).build();
    }

    @GetMapping(PathConstants.BOOK_MARK)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_STUDENT + "') ")
    @Operation(summary = "Get All BookMark Campaign Recruitment", security = @SecurityRequirement(name = "bearerAuth"))
    public BaseResponse getAllBookMarkCampaignRecruitment(){
        return BaseResponse.ok(studentService.bookMarks()).build();
    }

    @DeleteMapping(PathConstants.BOOK_MARK+PathConstants.ID)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_STUDENT + "') ")
    @Operation(summary = "UnBookMark Campaign Recruitment", security = @SecurityRequirement(name = "bearerAuth"))
    public BaseResponse unBookMarkCampaignRecruitment(@PathVariable(name = "id") String id ){
        return BaseResponse.ok(studentService.unBookMarkCampaignRecruitment(id)).build();
    }

    @DeleteMapping(PathConstants.APPLY+PathConstants.ID)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_STUDENT + "') ")
    @Operation(summary = "UnApply Campaign Recruitment", security = @SecurityRequirement(name = "bearerAuth"))
    public BaseResponse unApplyCampaignRecruitment(@PathVariable(name = "id") String id ){
        return BaseResponse.ok(studentService.unApplyCampaignRecruitment(id)).build();
    }

    @GetMapping(PathConstants.APPLY)
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_STUDENT + "') ")
    @Operation(summary = "Get All Apply Campaign Recruitment", security = @SecurityRequirement(name = "bearerAuth"))
    public BaseResponse getAllApplyCampaignRecruitment(){
        return BaseResponse.ok(studentService.listApply()).build();
    }

}
