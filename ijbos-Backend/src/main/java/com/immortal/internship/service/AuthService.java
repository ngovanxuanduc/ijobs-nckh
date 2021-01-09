package com.immortal.internship.service;

import com.immortal.internship.payload.request.ChangePasswordForm;
import com.immortal.internship.payload.request.LoginForm;
import com.immortal.internship.payload.response.BaseResponse;
import org.springframework.stereotype.Service;


@Service
public interface AuthService {
    /**
     * Validate information login from user, auth information
     * @param loginRequest
     * @return token
     */
    BaseResponse authenticateUser(LoginForm loginRequest);




    /**
     * Validate password from user, auth information, change user password
     * @param changePassReq
     * @return new Token for user
     */
    BaseResponse changeUserPassword(ChangePasswordForm changePassReq, boolean isWebToken);

    String logout(boolean isWebToken);
}
