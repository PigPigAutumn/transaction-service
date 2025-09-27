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
    private Long id;
    private String transactionId;
    private String account;
    private BigDecimal amount;
    private BigDecimal balance;
    private String type;
    private String status;
    private String counterparty;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
