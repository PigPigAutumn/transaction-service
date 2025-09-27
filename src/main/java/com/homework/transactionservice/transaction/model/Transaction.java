package com.homework.transactionservice.transaction.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity of transaction
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@TableName("t_transaction")
@Getter
@Setter
public class Transaction {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("transaction_id")
    private String transactionId;

    @TableField("account")
    private String account;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("balance")
    private BigDecimal balance;

    @TableField("type")
    private String type;

    @TableField("status")
    private String status;

    @TableField("counterparty")
    private String counterparty;

    @TableField("description")
    private String description;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
