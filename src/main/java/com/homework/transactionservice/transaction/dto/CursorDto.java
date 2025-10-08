package com.homework.transactionservice.transaction.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Cursor Dto
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Getter
@Setter
@NoArgsConstructor
public class CursorDto {

    /**
     * The creation time of the last record on the previous page.
     */
    @PastOrPresent
    private LocalDateTime lastCreateTime;

    /**
     * The id of the last record on the previous page.
     */
    @Nullable
    private Long lastId;

    /**
     * create a cursor
     *
     * @param lastId the last transaction id
     * @param lastCreateTime the last transaction create time
     */
    public CursorDto(@Nullable Long lastId, LocalDateTime lastCreateTime) {
        this.lastId = lastId;
        this.lastCreateTime = lastCreateTime;
    }
}
