package com.immortal.internship.controller;

import com.immortal.internship.constant.PathConstants;
import com.immortal.internship.constant.RoleConstants;
import com.immortal.internship.payload.request.CreateNotification;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(PathConstants.NOTIFICATION_PATH)
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @Operation(summary = "Create Notification", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAnyAuthority('" + RoleConstants.ROLE_ADMIN + "','" + RoleConstants.ROLE_TEACHER + "' )")
    @PostMapping(PathConstants.CREATE_NOTIFICATION)
    public BaseResponse createNotification(@Valid @RequestBody CreateNotification cNof){
        return BaseResponse.ok(notificationService.createNotification(cNof)).build();
    }

    @Operation(summary = "Get Group Notification", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAnyAuthority('" + RoleConstants.ROLE_ADMIN + "','" + RoleConstants.ROLE_TEACHER + "' )")
    @GetMapping(PathConstants.GET_GROUP_NOTIFICATION)
    public BaseResponse getGroupNotification(){
        return BaseResponse.ok(notificationService.getGroup()).build();
    }
}
