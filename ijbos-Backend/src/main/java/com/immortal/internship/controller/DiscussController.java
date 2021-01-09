package com.immortal.internship.controller;

import com.amazonaws.services.dynamodbv2.xspec.B;
import com.immortal.internship.constant.PathConstants;
import com.immortal.internship.constant.RoleConstants;
import com.immortal.internship.payload.request.DiscussForm;
import com.immortal.internship.payload.request.ReplyDiscussForm;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.service.ServiceImpl.DiscussService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(PathConstants.DISCUSS)
public class DiscussController {

    @Autowired
    private DiscussService discussService;


    @Operation(summary = "Discuss Create", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("isAuthenticated()")
    @PostMapping(PathConstants.CREATE)
    public BaseResponse createDiscuss(@Valid @RequestBody DiscussForm discussForm){
        return BaseResponse.ok(discussService.createDiscuss(discussForm.getRecruitmentID()
                , UUID.fromString(discussForm.getReceiver()))).build();
    }

    @Operation(summary = "Discuss Description", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("isAuthenticated()")
    @GetMapping(PathConstants.ID)
    public BaseResponse getDiscussByID(@PathVariable(name = "id") String discussID){
        return BaseResponse.ok(discussService.getDiscussByID(discussID)).build();
    }

    @Operation(summary = "Discuss Description", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("isAuthenticated()")
    @PostMapping(PathConstants.REPLY)
    public BaseResponse addReply(@Valid @RequestBody ReplyDiscussForm reply){
        return BaseResponse.ok(discussService.addReply(reply.getDiscussID(),reply.getContent())).build();
    }

    @Operation(summary = "Discuss Description", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("isAuthenticated()")
    @GetMapping(PathConstants.ALL)
    public BaseResponse getAllDiscuss(){
        return BaseResponse.ok(discussService.getAllDiscuss()).build();
    }


    @Operation(summary = "Get detail discuss by id", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("isAuthenticated()")
    @GetMapping(PathConstants.GET_FULL_DISCUSS_BY_ID)
    public BaseResponse getDetailDiscussById(@PathVariable(name = "disId") String disId){
        return BaseResponse.ok(discussService.getDetailDiscussById(disId)).build();
    }
}
