package com.immortal.internship.controller;

import com.immortal.internship.aop.annotation.TrackExecutionUser;
import com.immortal.internship.constant.PathConstants;
import com.immortal.internship.constant.RoleConstants;
import com.immortal.internship.payload.request.CreateRecruitmentForm;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.RecruitmentService;
import com.immortal.internship.aop.annotation.UUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController

@RequestMapping(PathConstants.RECRUITMENT_PATH)
@Validated
public class RecruitmentController {
    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    DBContext dbContext;

    @TrackExecutionUser
    @Operation(summary = "Get Campaign Recruitment Sort By Page", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(PathConstants.SORT_BY_PAGE)
    public BaseResponse getRecruitmentSortByPage(
            @RequestParam(value = PathConstants.PAGE_SIZE
                    , required = false
                    , defaultValue = PathConstants.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(value = PathConstants.PAGE
                    , defaultValue = PathConstants.DEFAULT_PAGE) Integer page,
            @RequestParam(value = PathConstants.SORT_BY
                    , defaultValue = PathConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = PathConstants.TYPE_SORT
                    , defaultValue = PathConstants.DEFAULT_TYPE_SORT) String typeSort) {

        return BaseResponse.ok(recruitmentService.getCampaignRecruitmentSortByPage(page, pageSize, sortBy, typeSort)).build();
    }

    @Operation(summary = "Create Campaign Recruitment", security = @SecurityRequirement(name = "bearerAuth"))
    @TrackExecutionUser
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "') or hasRole('" + RoleConstants.ROLE_COMPANY + "')")
    @PostMapping(PathConstants.CREATE)
    public BaseResponse createRecruitment(@Valid @RequestBody CreateRecruitmentForm createRecruitmentRequest) {
        return BaseResponse.ok(recruitmentService.createCampaignRecruitment(createRecruitmentRequest)).build();
    }

    @Operation(summary = "Update Campaign Recruitment", security = @SecurityRequirement(name = "bearerAuth"))
    @TrackExecutionUser
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_COMPANY + "') or hasRole('" + RoleConstants.ROLE_ADMIN + "') ")
    @PutMapping(value = PathConstants.ID)
    public BaseResponse updateRecruitment(@PathVariable(name = "id") String id,
                                          @Valid @RequestBody CreateRecruitmentForm update) {
        return BaseResponse.ok(recruitmentService.updateRecruitment(id, update)).build();
    }

    @Operation(summary = "Delete Campaign Recruitment", security = @SecurityRequirement(name = "bearerAuth"))
    @TrackExecutionUser
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_COMPANY + "') or hasRole('" + RoleConstants.ROLE_ADMIN + "') ")
    @DeleteMapping(value = PathConstants.ID)
    public BaseResponse deleteRecruitment(@PathVariable(name = "id") String id) {
        return BaseResponse.ok(recruitmentService.deleteRecruitment(id)).build();
    }

    @Operation(summary = "Approve Campaign Recruitment", security = @SecurityRequirement(name = "bearerAuth"))
    @TrackExecutionUser
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_ADMIN + "') ")
    @PutMapping(value = PathConstants.APPROVE + PathConstants.ID)
    public BaseResponse approveRecruitment(@PathVariable(name = "id") String id) {
        return BaseResponse.ok(recruitmentService.approveRecruitment(id)).build();
    }

    @TrackExecutionUser
    @Operation(summary = "Get Campaign Recruitment By ID", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("isAuthenticated()")
    @GetMapping(PathConstants.ID)
    public BaseResponse getCampaignRecruitmentByID(@PathVariable(name = "id") String id){
        return BaseResponse.ok(recruitmentService.getCampaignRecruitmentByID(id)).build();
    }

    @TrackExecutionUser
    @Operation(summary = "Get Campaign Recruitment By Create By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("isAuthenticated()")
    @GetMapping(PathConstants.GET_RECRUITMENT_BY_CREATE_BY)
    public BaseResponse getCampaignRecruitmentByCreateBy(@PathVariable(name = "id") @UUID String id){
        return BaseResponse.ok(recruitmentService.getRecruitmentsByCreateBy(java.util.UUID.fromString(id))).build();
    }




}
