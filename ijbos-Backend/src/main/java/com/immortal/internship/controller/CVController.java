package com.immortal.internship.controller;

import com.immortal.internship.aop.annotation.TrackExecutionUser;
import com.immortal.internship.aop.annotation.UUID;
import com.immortal.internship.constant.PathConstants;
import com.immortal.internship.constant.RoleConstants;
import com.immortal.internship.payload.request.CVForm;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.service.CVService;
import com.immortal.internship.service.ElasticSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathConstants.CV_PATH)
@Validated
public class CVController {


    @Autowired
    private CVService cvService;

    @Autowired
    private ElasticSearchService elasticSearchService;


    @TrackExecutionUser
    @Operation(summary = "Get Active CV By Student ID", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_TEACHER + "') or hasRole('"+ RoleConstants.ROLE_ADMIN +"') or hasRole('"+ RoleConstants.ROLE_COMPANY +"')"  )
    @GetMapping(PathConstants.GET_ACTIVE_CV_BY_STUDENT_ID)
    public BaseResponse getActiveCVByStudentId(@PathVariable(name = "id") @UUID String id){
        return BaseResponse.ok(cvService.getActiveCVById(java.util.UUID.fromString(id))).build();
    }

    @TrackExecutionUser
    @Operation(summary = "Get CV By Student ID", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_TEACHER + "') or hasRole('"+ RoleConstants.ROLE_ADMIN +"') or hasRole('"+ RoleConstants.ROLE_COMPANY +"')"  )
    @GetMapping(PathConstants.GET_CV_BY_STUDENT_ID)
    public BaseResponse getCVByStudentId(@PathVariable(name = "id") @UUID String id){
        return BaseResponse.ok(cvService.getCVById(java.util.UUID.fromString(id))).build();
    }

    @TrackExecutionUser
    @Operation(summary = "Get CV (Student)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_STUDENT + "')"  )
    @GetMapping(PathConstants.GET_CV)
    public BaseResponse getCVByStudentId(){
        return BaseResponse.ok(cvService.getCV()).build();
    }


    @TrackExecutionUser
    @Operation(summary = "Accept CV (Company)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_COMPANY + "') ")
    @PostMapping(PathConstants.ACCEPT_CV)
    public BaseResponse acceptCV(@RequestParam @UUID String stId
                                ,@RequestParam String reId){
        return BaseResponse.ok(cvService.acceptCV(java.util.UUID.fromString(stId),reId)).build();
    }

    @TrackExecutionUser
    @Operation(summary = "Reject CV (Company)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_COMPANY + "') ")
    @PostMapping(PathConstants.REJECT_CV)
    public BaseResponse rejectCV(@RequestParam @UUID String stId
                                 ,@RequestParam String reId ){
        return BaseResponse.ok(cvService.rejectCV(java.util.UUID.fromString(stId),reId)).build();
    }

    @TrackExecutionUser
    @Operation(summary = "Approve CV (School)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_TEACHER + "') or hasRole('"+ RoleConstants.ROLE_ADMIN +"')" )
    @PostMapping(PathConstants.APPROVE_CV)
    public BaseResponse approveCV(@PathVariable(name = "id") @UUID String id ){
        return BaseResponse.ok(cvService.approveCV(java.util.UUID.fromString(id))).build();
    }

    @TrackExecutionUser
    @Operation(summary = "Cancel CV (School)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_TEACHER + "') or hasRole('"+ RoleConstants.ROLE_ADMIN +"')" )
    @PostMapping(PathConstants.CANCEL_CV)
    public BaseResponse cancelCV(@PathVariable(name = "id") @UUID String id ){
        return BaseResponse.ok(cvService.cancelCV(java.util.UUID.fromString(id))).build();
    }

    @TrackExecutionUser
    @Operation(summary = "Create CV (Student)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_STUDENT + "') ")
    @GetMapping(PathConstants.CREATE_CV)
    public BaseResponse createCV(CVForm cvForm){
        return BaseResponse.ok(cvService.createCV(cvForm)).build();
    }

    @TrackExecutionUser
    @Operation(summary = "Get student list (School)", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_TEACHER + "') or hasRole('"+ RoleConstants.ROLE_ADMIN +"')" )
    @GetMapping(PathConstants.GET_STUDENT_LIST)
    public BaseResponse getStudentList(){
        return BaseResponse.ok(cvService.getStudentList()).build();
    }


    @TrackExecutionUser
    @Operation(summary = "Get student suggest CV list", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_COMPANY + "') or hasRole('"+ RoleConstants.ROLE_ADMIN +"')" )
    @GetMapping(PathConstants.GET_SUGGEST_CV_LIST)
    public BaseResponse getCVSuggestByRecruitmentId(@PathVariable(name = "recId")  String recId){
        return BaseResponse.ok(elasticSearchService.getCVSuggestByRecruitment(recId)).build();
    }
}
