package com.immortal.internship.controller;

import com.immortal.internship.constant.PathConstants;
import com.immortal.internship.payload.request.ChangePasswordForm;
import com.immortal.internship.payload.request.LoginForm;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.payload.response.SearchCVResponse;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.AuthService;
import com.immortal.internship.service.ElasticSearchService;
import com.immortal.internship.service.RecruitmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PathConstants.AUTH_PATH)
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    RecruitmentService recruitmentService;

    @Operation(summary = "Login")
    @PostMapping(PathConstants.LOGIN)
    public ResponseEntity<BaseResponse> authenticateWebUser(@Valid @RequestBody LoginForm loginRequest) {
        BaseResponse jwtResponse = authService.authenticateUser(loginRequest);
        return new ResponseEntity<BaseResponse>(jwtResponse, HttpStatus.OK);
    }


    @Operation(summary = "Change Password", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(PathConstants.CHANGE_PASS)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordForm changePassRequest) {
        BaseResponse jwtResponse = authService.changeUserPassword(changePassRequest, PathConstants.WEB_TOKEN);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @Operation(summary = "Logout Web", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(PathConstants.LOGOUT_WEB)
    @PreAuthorize("isAuthenticated()")
    public BaseResponse logoutWeb(){
        return BaseResponse.ok(authService.logout(PathConstants.WEB_TOKEN)).build();
    }

    @Operation(summary = "Logout Mobile", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(PathConstants.LOGOUT_MOBILE)
    @PreAuthorize("isAuthenticated()")
    public BaseResponse logoutMobile(){
        return BaseResponse.ok(authService.logout(!PathConstants.WEB_TOKEN)).build();
    }

    @Operation(summary = "Recruitment For Web Page")
    @GetMapping(PathConstants.PUBLIC)
    public BaseResponse getRecruitmentPublic(
            @RequestParam(value = PathConstants.PAGE_SIZE
                    , required = false, defaultValue = PathConstants.DEFAULT_PAGE_SIZE) Integer pageSize
            , @RequestParam(value = PathConstants.PAGE
                , defaultValue = PathConstants.DEFAULT_PAGE) Integer page){
        return BaseResponse.ok(recruitmentService.getRecruitmentPublic(page,pageSize)).build();
    }

    @Autowired
    ElasticSearchService elasticSearchService;
//    @Autowired
//    DBContext dbContext;
    @GetMapping("/haha")
    public List<SearchCVResponse> haha(){
//        Map<SearchCVResponse, Double> cvSuggests = elasticSearchService.getAllCVBySkillss(elasticSearchService.recommendations("database"));
//        cvSuggests.forEach(System.out::println);
//        return cvSuggests;
//        Arrays.stream(dbContext.customRepository.getEmailByGroupID(7)).forEach(System.out::println);

        return elasticSearchService.getCVSuggestByRecruitment("i7fo4tXdXe9xwqu");

    }
}
