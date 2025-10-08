package com.homework.transactionservice.transaction.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TransactionVO
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Getter
@Setter
public class TransactionVO {
    /**
     * The id of the database record.
     */
    private Long id;

    /**
     * Transaction id.
     */
    private String transactionId;

    /**
     * Transaction account.
     */
    private String account;

    /**
     * Transaction amount.<br>
     * Positive values represent income and negative values represent expenditure.
     */
    private BigDecimal amount;

    /**
     * Post-transaction account balance.
     */
    private BigDecimal balance;

    /**
     * Transaction type.<br>
     * The following values are available: TRANSFER, DEPOSIT, WITHDRAW, PAYMENT.
     */
    private String type;

    /**
     * Transaction status.<br>
     * The following values are available: SUCCESS, FAILED, PROCESSING.
     */
    private String status;

    /**
     * Counterparty information.
     */
    private String counterparty;

    /**
     * Transaction description.
     */
    private String description;

    /**
     * Transaction creation time.
     */
    private LocalDateTime createTime;

    /**
     * Transaction update time.
     */
    private LocalDateTime updateTime;
}
