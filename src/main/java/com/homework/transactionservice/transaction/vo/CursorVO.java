package com.homework.transactionservice.transaction.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * CursorVO
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Getter
@Setter
public class CursorVO {
    private LocalDateTime lastCreateTime;
    private Long lastId;
    private boolean hasMore;
}
