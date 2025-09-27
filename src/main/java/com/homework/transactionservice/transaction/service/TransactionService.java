package com.homework.transactionservice.transaction.service;

import com.homework.transactionservice.transaction.dto.TransactionCreateDto;
import com.homework.transactionservice.transaction.dto.TransactionQueryDto;
import com.homework.transactionservice.transaction.dto.TransactionUpdateDto;
import com.homework.transactionservice.transaction.model.CursorPage;
import com.homework.transactionservice.transaction.model.Transaction;

/**
 * Transaction Service
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public interface TransactionService {

    /**
     * create transaction
     *
     * @param transaction transactionCreateDto
     * @return transaction
     */
    Transaction create(TransactionCreateDto transaction);

    /**
     * delete transaction by id
     *
     * @param id the transaction id
     * @return transaction that has been deleted
     */
    Transaction delete(Long id);

    /**
     * update transaction status by id
     *
     * @param transaction transactionUpdateDto
     * @return transaction that has been updated
     */
    Transaction update(TransactionUpdateDto transaction);

    /**
     * query page by cursor
     *
     * @param query transactionQueryDto
     * @return cursor pagination result
     */
    CursorPage pageQuery(TransactionQueryDto query);
}
