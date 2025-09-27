package com.homework.transactionservice.transaction.dto;

import com.homework.transactionservice.validation.PatternRegex;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * TransactionCreateDto
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Getter
@Setter
public class TransactionCreateDto {

    @NotEmpty
    @Pattern(regexp = PatternRegex.COMMON_REGEX_64)
    private String transactionId;

    @NotEmpty
    @Pattern(regexp = PatternRegex.COMMON_REGEX_64)
    private String account;

    @NotNull
    private BigDecimal amount;

    @DecimalMin(value = "0")
    private BigDecimal balance;

    @NotEmpty
    @Pattern(regexp = PatternRegex.COMMON_REGEX_16)
    private String type;

    @NotEmpty
    @Pattern(regexp = PatternRegex.COMMON_REGEX_16)
    private String status;

    @NotEmpty
    @Pattern(regexp = PatternRegex.COMMON_REGEX_64)
    private String counterparty;

    @Pattern(regexp = PatternRegex.COMMON_REGEX_128)
    private String description;
}