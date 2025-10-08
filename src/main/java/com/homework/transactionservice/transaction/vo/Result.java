package com.homework.transactionservice.transaction.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Result
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Getter
@Setter
@NoArgsConstructor
public class Result<T> {
    private static final int SUCCESS = 200;
    private static final String SUCCESS_MSG = "success";

    /**
     * Result code.<br>
     * "200" represents success.
     */
    private int code;

    /**
     * The readable information corresponding to the result code.
     */
    private String message;

    /**
     * Business data.
     */
    private T data;

    /**
     * create result with specified data setting code and message to the success value
     *
     * @param data data
     */
    public Result(T data) {
        this.code = SUCCESS;
        this.message = SUCCESS_MSG;
        this.data = data;
    }

    /**
     * create result with specified data setting code and message to the success value
     *
     * @param data data
     * @return result
     * @param <T> data type
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * create result with specified error code and error message
     *
     * @param code error code
     * @param message error message
     * @return fail result
     */
    public static Result<Void> fail(int code, String message) {
        Result<Void> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(null);
        return result;
    }
}
