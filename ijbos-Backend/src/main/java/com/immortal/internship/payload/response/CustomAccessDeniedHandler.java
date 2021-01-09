package com.immortal.internship.payload.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.immortal.internship.constant.MessageConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
            , AccessDeniedException e) throws IOException, ServletException {
        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.warn("User: " + auth.getName()
                    + " attempted to access the protected URL: "
                    + httpServletRequest.getRequestURI());
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(
                    BaseResponse.error(ErrorResponse.builder()
                            .userMessage(MessageConstants.ForUser.ACCESS_DENIED)
                            .internalMessage(MessageConstants.ForSystem.FORBIDDEN)
                            .code(MessageConstants.ResultCode.NOT_PERMISSION)
                            .build()
                    ).build());
            //System.out.println(json);
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(json);

        } catch (IOException e1) {
            e.printStackTrace();
        }
    }
}
