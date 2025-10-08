package com.homework.transactionservice.transaction.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * PageResult
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Getter
@Setter
@NoArgsConstructor
public class PageResult<T> extends Result<List<T>> {

    /**
     * The pagination cursor of the previous page.
     */
    private CursorVO cursor;

    /**
     * create a page result
     *
     * @param data page data
     * @param cursor cursor info
     */
    public PageResult(List<T> data, CursorVO cursor) {
        super(data);
        this.cursor = cursor;
    }
}
