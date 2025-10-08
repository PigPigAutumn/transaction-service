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

    /**
     * Transaction id.
     */
    @NotEmpty
    @Pattern(regexp = PatternRegex.COMMON_REGEX_64)
    private String transactionId;

    /**
     * Transaction account.
     */
    @NotEmpty
    @Pattern(regexp = PatternRegex.COMMON_REGEX_64)
    private String account;

    /**
     * Transaction amount.<br>
     * Positive values represent income and negative values represent expenditure.
     */
    @NotNull
    private BigDecimal amount;

    /**
     * Post-transaction account balance.
     */
    @DecimalMin(value = "0")
    private BigDecimal balance;

    /**
     * Transaction type.<br>
     * The following values are available: TRANSFER, DEPOSIT, WITHDRAW, PAYMENT.
     */
    @NotEmpty
    @Pattern(regexp = PatternRegex.COMMON_REGEX_16)
    private String type;

    /**
     * Transaction status.<br>
     * The following values are available: SUCCESS, FAILED, PROCESSING.
     */
    @NotEmpty
    @Pattern(regexp = PatternRegex.COMMON_REGEX_16)
    private String status;

    /**
     * Counterparty information.
     */
    @NotEmpty
    @Pattern(regexp = PatternRegex.COMMON_REGEX_64)
    private String counterparty;

    /**
     * Transaction description.
     */
    @Pattern(regexp = PatternRegex.COMMON_REGEX_128)
    private String description;
}