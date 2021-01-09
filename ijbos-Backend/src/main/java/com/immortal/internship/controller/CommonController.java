package com.immortal.internship.controller;


import com.immortal.internship.constant.PathConstants;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.service.CommonService;
import com.immortal.internship.service.RecruitmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConstants.AFTER_LOGIN_PATH)
public class CommonController {
    @Autowired
    CommonService commonService;

    @Autowired
    RecruitmentService recruitmentService;

    @Operation(summary = "After Login", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("isAuthenticated()")
    @GetMapping(PathConstants.FORWARD_SLASH)
    public BaseResponse loadInf() {
        return BaseResponse.ok(commonService.loadInformation()).build();
    }

    @Operation(summary = "Get Recruitment By Create By Id Public")
    @GetMapping(PathConstants.GET_RECRUITMENT_BY_ID_PUBLIC)
    public BaseResponse getRecruitmentByIdPublic(@PathVariable(name = "id") String id){
        return BaseResponse.ok(recruitmentService.getRecruitmentById(id)).build();
    }
}
