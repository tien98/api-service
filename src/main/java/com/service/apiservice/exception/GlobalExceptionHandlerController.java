package com.service.apiservice.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.service.apiservice.config.ResponseDataConfiguration;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @Bean
    public ErrorAttributes errorAttributes() {
        // Hide exception field in the return object
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                return super.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults().excluding(ErrorAttributeOptions.Include.EXCEPTION));
            }
        };
    }

    @ExceptionHandler(CustomException.class)
    public void handleCustomException(HttpServletResponse res, CustomException ex) throws IOException {
        res.sendError(ex.getHttpStatus().value(), ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
    }

//    @ExceptionHandler(Exception.class)
//    public void handleException(HttpServletResponse res) throws IOException {
//        res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
//    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    protected ResponseEntity<?> handleBusinessException(BusinessException ex) {
        if (ex.getHttpStatus() == null) {
            return ResponseDataConfiguration.error(BAD_REQUEST, ex.getErrorCode(), ex.getContent());
        } else {
            return ResponseDataConfiguration.error(ex.getHttpStatus(), ex.getErrorCode(), ex.getContent());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseDataConfiguration.error(BAD_REQUEST, errors);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    protected ResponseEntity<?> handleException(Exception ex) {
        return ResponseDataConfiguration.error(INTERNAL_SERVER_ERROR, ErrorCode.SERVER_ERROR, ex.getMessage());
    }

}
