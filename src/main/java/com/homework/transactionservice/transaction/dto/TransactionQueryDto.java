package com.homework.transactionservice.transaction.dto;

import com.homework.transactionservice.validation.PatternRegex;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * TransactionQueryDto
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Getter
@Setter
public class TransactionQueryDto {

    /**
     * Transaction account.
     */
    @Pattern(regexp = PatternRegex.COMMON_REGEX_64)
    private String account;

    /**
     * Transaction type.<br>
     * The following values are available: TRANSFER, DEPOSIT, WITHDRAW, PAYMENT.
     */
    @Pattern(regexp = PatternRegex.COMMON_REGEX_16)
    private String type;

    /**
     * Transaction status.<br>
     * The following values are available: SUCCESS, FAILED, PROCESSING.
     */
    @Pattern(regexp = PatternRegex.COMMON_REGEX_16)
    private String status;

    /**
     * The starting time of the transaction time range.
     */
    @PastOrPresent
    private LocalDateTime startTime;

    /**
     * The end time of the transaction time range.
     */
    @PastOrPresent
    private LocalDateTime endTime;

    /**
     * The pagination cursor of the previous page.
     */
    @Nullable
    private CursorDto cursor;

    /**
     * Page size.
     */
    @Min(value = 1L)
    private Integer pageSize;
}
