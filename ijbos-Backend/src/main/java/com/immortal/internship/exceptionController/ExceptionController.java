package com.immortal.internship.exceptionController;

import com.immortal.internship.constant.MessageConstants;
import com.immortal.internship.exception.*;
import com.immortal.internship.payload.response.BaseResponse;
import com.immortal.internship.payload.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    //Handle multiple exceptions extend BaseException

    @ExceptionHandler({InvalidParamException.class
            ,InvalidCurrentUserException.class
            , AccessDeniedException.class
            , NotCompleteProfileException.class
            , BaseException.class})
    public ResponseEntity<BaseResponse> handleBaseException(BaseException ex) {
        ErrorResponse err = ErrorResponse.builder()
                .code(ex.getStatusCode())
                .internalMessage(ex.getErrorMessage())
                .userMessage(ex.getUserMessage())
                .build();
        return new ResponseEntity<BaseResponse>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Base4xxException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse<Object> handleAccessDeniedException(Base4xxException ex){
        return ErrorResponse.builder()
                .code(ex.getStatusCode())
                .internalMessage(ex.getErrorMessage())
                .userMessage(ex.getUserMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse err = ErrorResponse.builder()
                .code(MessageConstants.ResultCode.BAD_REQUEST_VALID_PARAMS)
                .internalMessage(ex.getMessage())
                .userMessage(MessageConstants.ForUser.INVALID_PARAMS)
                .errors(errors)
                .build();
        return err;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse handleUUIDExceptions(ConstraintViolationException ex){

        ErrorResponse err = ErrorResponse.builder()
                .code(MessageConstants.ResultCode.BAD_REQUEST_VALID_PARAMS)
                .internalMessage(ex.getMessage())
                .userMessage(MessageConstants.ForUser.INVALID_UUID)
                .build();
        return err;
    }

//    // Xử lý tất cả các exception chưa được khai báo
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponse handlerException(Exception ex, WebRequest req) {
//        // Log err
//        return ErrorResponse.builder().errors(ex).build();
//    }

}
