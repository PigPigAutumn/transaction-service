package com.homework.transactionservice.exception;

import com.homework.transactionservice.exception.enums.ErrorCode;
import lombok.Getter;

/**
 * BaseException
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Getter
public class BaseException extends RuntimeException {

    private final int code;

    /**
     * create a BaseException instance
     *
     * @param errorCode error code
     */
    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
