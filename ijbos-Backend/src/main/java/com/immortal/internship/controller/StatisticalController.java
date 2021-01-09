package com.immortal.internship.controller;

import com.immortal.internship.aop.annotation.UUID;
import com.immortal.internship.constant.PathConstants;
import com.immortal.internship.constant.RoleConstants;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.service.StatisticalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConstants.STATISTICAL)
@Validated
public class StatisticalController {
    @Autowired
    StatisticalService statisticalService;

    @Operation(summary = "Get student intern by company id", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_COMPANY + "') or hasRole('" + RoleConstants.ROLE_ADMIN + "') ")
    @GetMapping(PathConstants.GET_ALL_STUDENT_INTERN_BY_COMPANY_ID)
    public BaseResponse getAllInternStudentByCompanyId(@PathVariable(name = "comId") @UUID String comId){
        return BaseResponse.ok(statisticalService
                .getAllInternStudentByCompanyId(java.util.UUID.fromString(comId))).build();
    }



    @Operation(summary = "Get statistical", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "') ")
    @GetMapping(PathConstants.GET_STATISTICAL)
    public BaseResponse getStatistical(){
        return BaseResponse.ok(statisticalService.getStatistical()).build();
    }
}
