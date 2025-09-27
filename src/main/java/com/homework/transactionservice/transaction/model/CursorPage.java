package com.homework.transactionservice.transaction.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Cursor Page
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Getter
@Setter
@NoArgsConstructor
public class CursorPage {

    private List<Transaction> data;
    private boolean hasMore;
    private LocalDateTime lastCreateTime;
    private Long lastId;

    /**
     * create a cursor page
     *
     * @param data page data
     * @param hasMore whether has next page
     */
    public CursorPage(List<Transaction> data, boolean hasMore) {
        this.data = data;
        this.hasMore = hasMore;

        if (!CollectionUtils.isEmpty(data)) {
            Transaction last = data.get(data.size() - 1);
            this.lastCreateTime = last.getCreateTime();
            this.lastId = last.getId();
        }
    }
}
