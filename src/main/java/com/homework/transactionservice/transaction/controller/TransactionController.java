package com.homework.transactionservice.transaction.controller;

import com.homework.transactionservice.transaction.model.CursorPage;
import com.homework.transactionservice.transaction.vo.CursorVO;
import com.homework.transactionservice.transaction.vo.PageResult;
import com.homework.transactionservice.transaction.vo.Result;
import com.homework.transactionservice.transaction.vo.*;
import com.homework.transactionservice.transaction.dto.TransactionCreateDto;
import com.homework.transactionservice.transaction.dto.TransactionQueryDto;
import com.homework.transactionservice.transaction.dto.TransactionUpdateDto;
import com.homework.transactionservice.transaction.model.Transaction;
import com.homework.transactionservice.transaction.model.TransactionConverter;
import com.homework.transactionservice.transaction.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Controller of transaction API
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@RestController
@RequestMapping("/transaction")
@Validated
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    /**
     * create transaction
     *
     * @param transactionCreateDto transaction without id, createTime and updateTime
     * @return transaction that all the fields are completed
     */
    @PostMapping("")
    public Result<TransactionVO> create(@Valid @RequestBody TransactionCreateDto transactionCreateDto) {
        Transaction transaction = transactionService.create(transactionCreateDto);
        return Result.success(TransactionConverter.toVO(transaction));
    }

    /**
     * delete transaction
     *
     * @param id transaction id to be deleted
     * @return transaction that has been deleted
     */
    @DeleteMapping("/{id}")
    public Result<TransactionVO> delete(@NotNull @PathVariable Long id) {
        Transaction transaction = transactionService.delete(id);
        return Result.success(TransactionConverter.toVO(transaction));
    }

    /**
     * update transaction
     *
     * @param transactionUpdateDto transaction id and transaction status
     * @return transaction that has been updated
     */
    @PutMapping("")
    public Result<TransactionVO> update(@Valid @RequestBody TransactionUpdateDto transactionUpdateDto) {
        Transaction transaction = transactionService.update(transactionUpdateDto);
        return Result.success(TransactionConverter.toVO(transaction));
    }

    /**
     * list transaction
     *
     * @param transactionQueryDto transaction filter parameters
     * @return transaction list with cursor
     */
    @PostMapping("/list")
    public PageResult<TransactionVO> pageQuery(@Valid @RequestBody TransactionQueryDto transactionQueryDto) {
        CursorPage page = transactionService.pageQuery(transactionQueryDto);

        if (page == null || CollectionUtils.isEmpty(page.getData())) {
            return new PageResult<>(Collections.emptyList(), null);
        }

        CursorVO cursor = new CursorVO();
        cursor.setLastId(page.getLastId());
        cursor.setLastCreateTime(page.getLastCreateTime());
        cursor.setHasMore(page.isHasMore());

        List<TransactionVO> transactionVOList = TransactionConverter.toVOList(page.getData());

        return new PageResult<>(transactionVOList, cursor);
    }
}
