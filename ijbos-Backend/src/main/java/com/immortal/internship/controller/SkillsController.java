package com.immortal.internship.controller;

import com.immortal.internship.aop.annotation.TrackExecutionUser;
import com.immortal.internship.constant.PathConstants;
import com.immortal.internship.constant.RoleConstants;
import com.immortal.internship.payload.request.CreateSkillsForm;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping(PathConstants.SKILLS)
public class SkillsController {
    @Autowired
    private SkillService service;

    @GetMapping
    @Operation(summary = "Get Skills", security = @SecurityRequirement(name = "bearerAuth"))
    public BaseResponse getAllSkills(){
        return BaseResponse.ok(service.getAllSkills()).build();
    }


    @PostMapping(PathConstants.CREATE)
    @Operation(summary = "ADD Skills", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_COMPANY + "') or hasRole('" + RoleConstants.ROLE_ADMIN + "') ")
    public BaseResponse addSkill(@Valid @RequestBody CreateSkillsForm skillsForm){
//        skillsForm.getSkills().forEach(System.out::println);
        return BaseResponse.ok(service.addListSkills(skillsForm.getSkills())).build();
//        return BaseResponse.ok("service.addListSkills(skills)").build();
    }

}
