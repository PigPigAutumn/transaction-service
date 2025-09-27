package com.homework.transactionservice.exception.enums;

import lombok.Getter;

/**
 * Error Code
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Getter
public enum ErrorCode {
    INTERNAL_ERROR(500, "error.internal_error"),

    TRANSACTION_NOT_EXISTS(1001, "transaction.not_exists"),
    TRANSACTION_ALREADY_EXISTS(1002, "transaction.already_exists"),
    TRANSACTION_INSERT_FAILED(1003, "transaction.insert_failed"),
    TRANSACTION_UPDATE_FAILED(1004, "transaction.update_failed"),
    TRANSACTION_DELETE_FAILED(1005, "transaction.delete_failed"),

    VALIDATION_FAILED(2001, "validation.field_invalid");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
