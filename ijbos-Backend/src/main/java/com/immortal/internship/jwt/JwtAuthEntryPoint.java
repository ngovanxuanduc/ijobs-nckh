package com.immortal.internship.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.immortal.internship.constant.MessageConstants;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.payload.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e)
            throws IOException, ServletException {
        logger.error("Unauthorized error. Message - {}", e.getMessage());
        //httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, MessageConstants.ForUser.USER_NOT_FOUND_WITH_USERNAME_OR_PASSWORD);
        /**
         * custome json tra ve o day duoc
         */
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(
                    BaseResponse.error(ErrorResponse.builder()
                            .userMessage(MessageConstants.ForUser.USER_NOT_FOUND_WITH_USERNAME_OR_PASSWORD)
                            .internalMessage(e.getMessage())
                            .code(MessageConstants.ResultCode.BAD_CREDENTIALS)
                            .build()
                    ).build());
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(json);

        } catch (IOException e1) {
            e.printStackTrace();
        }
    }
}
