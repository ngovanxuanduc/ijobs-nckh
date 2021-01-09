package com.immortal.internship.service.ServiceImpl;

import com.immortal.internship.aop.annotation.TrackExecutionUser;
import com.immortal.internship.constant.LoginConstant;
import com.immortal.internship.constant.MessageConstants;
import com.immortal.internship.constant.PathConstants;
import com.immortal.internship.constant.ResultMessCode;
import com.immortal.internship.entity.AccountEntity;
import com.immortal.internship.entity.CurrentTokenEntity;
import com.immortal.internship.exception.Base4xxException;
import com.immortal.internship.exception.InvalidCurrentUserException;
import com.immortal.internship.exception.InvalidParamException;
import com.immortal.internship.jwt.JwtProvider;
import com.immortal.internship.payload.request.ChangePasswordForm;
import com.immortal.internship.payload.request.LoginForm;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.repository.DBContext;
import com.immortal.internship.service.AuthService;
import com.immortal.internship.service.UserPrinciple;
import com.immortal.internship.utility.UserAccountValidationUtil;
import com.immortal.internship.utility.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, AuthService {
    @Autowired
    private DBContext dbContext;

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    UserProvider userProvider;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("ExceptionAccessDenied")
    Base4xxException accessDenied;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AccountEntity account = dbContext.accountRepository.findByUserName(s)
                .orElseThrow(()
                        -> new UsernameNotFoundException(MessageConstants.ForUser.USER_NOT_FOUND_WITH_USERNAME_OR_PASSWORD)
                );
        return new UserPrinciple(account);
    }

    //
    @Override
    public BaseResponse authenticateUser(LoginForm loginRequest) {
        if (!UserAccountValidationUtil.isValidateUserName(loginRequest.getUsername())) {
            throw new InvalidParamException(MessageConstants.ForSystem.INVALID_PARAM
                    ,MessageConstants.ForUser.INVALID_NAME,MessageConstants.ResultCode.INVALID_NAME);
        }
        /**
         * doan nay ko can bat BadCredentialsException
         * custom lai AuthenticationEntryPoint la duoc (JwtAuthEntryPoint)
         */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        //TODO: check type account ?
        boolean isMobileLogin = loginRequest.getSystemType() == LoginConstant.STUDENT_MOBILE;
        checkUserLoginCorrectToSystem(loginRequest.getSystemType(), (UserPrinciple) authentication.getPrincipal());
//        System.out.println("----- Tap quyen Cua UserLogin -----");
//        ((UserPrinciple) authentication.getPrincipal()).getAuthorities().forEach(System.out::println);
//        System.out.println("------------------------------------------");
        return generateJwtToken(authentication, ResultMessCode.SUCCESS, isMobileLogin);
    }

    private void checkUserLoginCorrectToSystem(int systemType, UserPrinciple u){
        if (systemType == LoginConstant.STUDENT_WEB || systemType == LoginConstant.STUDENT_MOBILE){
            if (!userProvider.isStudentAccount(u)){
                throw accessDenied;
            }
        } else if (systemType == LoginConstant.SCHOOL_OR_COMPANY){
            if (userProvider.isStudentAccount(u)){
                throw accessDenied;
            }
        } else if (systemType == LoginConstant.COMPANY){
            if (!userProvider.isCompanyAccount(u)){
                throw accessDenied;
            }
        } else if (systemType == LoginConstant.SCHOOL){
            if (!userProvider.isSchoolAccount(u)){
                throw accessDenied;
            }
        } else {
            throw accessDenied;
        }
    }

    /**
     * tao token dua tren authentication, luu token xuong DB
     *
     * @param authentication
     * @return
     */
    private BaseResponse generateJwtToken(Authentication authentication
            , ResultMessCode resultMessCode
            , boolean isMobileLogin) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        String userName = ((UserPrinciple) authentication.getPrincipal()).getUsername();

        CurrentTokenEntity tokenSave = dbContext.currentTokenRepository.findByUserName(userName)
                .orElse(CurrentTokenEntity.builder().userName(userName).build());
        if (isMobileLogin){
            tokenSave.setMobileToken(jwt);
        } else {
            tokenSave.setWebToken(jwt);
        }
        dbContext.currentTokenRepository.save(tokenSave);
        return BaseResponse.ok(jwt).build();
    }

    @Override
    public BaseResponse changeUserPassword(ChangePasswordForm changePassReq, boolean isWebToken) {
        if (!UserAccountValidationUtil.isValidatePassword(changePassReq.getNewPassword())) {
            throw new InvalidParamException(MessageConstants.ForSystem.INVALID_PARAM
                    ,MessageConstants.ForUser.INVALID_PASSWORD
                    ,MessageConstants.ResultCode.INVALID_PASSWORD);
        }
        //get current user
        Optional<AccountEntity> currentAccount = dbContext.accountRepository.
                findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        //Check current user
        Optional.ofNullable(currentAccount)
                .orElseThrow(() -> new InvalidCurrentUserException(MessageConstants.ForSystem.INVALID_CURRENT_USER
                        ,MessageConstants.ForUser.USER_NOT_FOUND_WITH_USERNAME_OR_PASSWORD
                        ,MessageConstants.ResultCode.INVALID_CURRENT_USER
                ));
        //check old password match on db
        if (!passwordEncoder.matches(changePassReq.getOldPassword(), currentAccount.get().getPassword())) {
            throw new InvalidParamException(MessageConstants.ForSystem.INVALID_PARAM
                    ,MessageConstants.ForUser.INCORRECT_PASSWORD
                    ,MessageConstants.ResultCode.INVALID_OLD_PASSWORD);
        }
        //save user in db
        currentAccount.get().setPassword(passwordEncoder.encode(changePassReq.getNewPassword()));
        dbContext.accountRepository.save(currentAccount.get());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        currentAccount.get().getUserName(),
                        changePassReq.getNewPassword()
                )
        );
        return generateJwtToken(authentication, ResultMessCode.CHANGE_PASS_SUCCESS, isWebToken);
    }

    @Override
    public String logout(boolean isWebToken) {
        Optional<CurrentTokenEntity> currentToken = dbContext.currentTokenRepository
                .findByUserName(userProvider.getCurrentUser().getUsername());
        Optional.ofNullable(currentToken)
                .orElseThrow(() -> new InvalidCurrentUserException(MessageConstants.ForSystem.INVALID_CURRENT_USER
                        ,MessageConstants.ForUser.USER_NOT_FOUND_WITH_USERNAME_OR_PASSWORD
                        ,MessageConstants.ResultCode.INVALID_CURRENT_USER
                ));
        if(isWebToken==PathConstants.WEB_TOKEN){
            currentToken.get().setWebToken(null);
        }else{
            currentToken.get().setMobileToken(null);
        }
        dbContext.currentTokenRepository.save(currentToken.get());
        return MessageConstants.ForUser.LOGOUT_SUCCESS;
    }


}