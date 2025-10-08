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

    /**
     * The creation time of the last record on the previous page.
     */
    private LocalDateTime lastCreateTime;

    /**
     * The id of the last record on the previous page.
     */
    private Long lastId;

    /**
     * return true when next page existing, otherwise, return false.
     */
    private boolean hasMore;
}
