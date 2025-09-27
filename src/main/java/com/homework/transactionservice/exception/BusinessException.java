package com.homework.transactionservice.exception;

import com.homework.transactionservice.exception.enums.ErrorCode;

/**
 * BusinessException
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class BusinessException extends BaseException {

    /**
     * create a BusinessException instance
     *
     * @param errorCode error code
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
