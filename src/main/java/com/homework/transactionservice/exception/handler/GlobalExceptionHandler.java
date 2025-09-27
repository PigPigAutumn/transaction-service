package com.homework.transactionservice.exception.handler;

import com.homework.transactionservice.transaction.vo.Result;
import com.homework.transactionservice.exception.BaseException;
import com.homework.transactionservice.exception.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Global exception handler
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    /**
     * handle business exception
     *
     * @param e businessException throw by service
     * @return VO Result
     */
    @ExceptionHandler(BaseException.class)
    public Result<Void> handleBaseException(BaseException e) {
        return Result.fail(e.getCode(), getMessage(e.getMessage(), e.getMessage()));
    }

    /**
     * handle validation exception
     *
     * @param ex validation exception
     * @return VO Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorFields = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            errorFields.add(fieldName);
        });
        List<String> errorMessages = Collections.singletonList(String.join(",", errorFields));
        return Result.fail(
                ErrorCode.VALIDATION_FAILED.getCode(),
                getMessage(ErrorCode.VALIDATION_FAILED.getMessage(), ErrorCode.VALIDATION_FAILED.getMessage(), errorMessages));
    }

    /**
     * handle unexpected exception
     *
     * @param e unexpected exception
     * @return VO Result
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        return Result.fail(ErrorCode.INTERNAL_ERROR.getCode(),
                getMessage(ErrorCode.INTERNAL_ERROR.getMessage(), ErrorCode.INTERNAL_ERROR.getMessage()));
    }

    private String getMessage(String code, String defaultMessage) {
        if (this.messageSource == null) {
            return defaultMessage;
        }
        return messageSource.getMessage(code, null, defaultMessage, getLocale());
    }

    private String getMessage(String code, String defaultMessage, List<String> argList) {
        if (this.messageSource == null) {
            return defaultMessage;
        }
        String[] args = null;
        if (!CollectionUtils.isEmpty(argList)) {
            args = argList.toArray(new String[0]);
        }
        return messageSource.getMessage(code, args, defaultMessage, getLocale());
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
