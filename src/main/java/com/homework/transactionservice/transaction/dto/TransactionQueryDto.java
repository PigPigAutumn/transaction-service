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

    @Pattern(regexp = PatternRegex.COMMON_REGEX_64)
    private String account;

    @Pattern(regexp = PatternRegex.COMMON_REGEX_16)
    private String type;

    @Pattern(regexp = PatternRegex.COMMON_REGEX_16)
    private String status;

    @PastOrPresent
    private LocalDateTime startTime;

    @PastOrPresent
    private LocalDateTime endTime;

    @Nullable
    private CursorDto cursor;

    @Min(value = 1L)
    private Integer pageSize;
}
