package com.homework.transactionservice.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homework.transactionservice.exception.BusinessException;
import com.homework.transactionservice.exception.enums.ErrorCode;
import com.homework.transactionservice.transaction.dto.CursorDto;
import com.homework.transactionservice.transaction.dto.TransactionCreateDto;
import com.homework.transactionservice.transaction.dto.TransactionQueryDto;
import com.homework.transactionservice.transaction.dto.TransactionUpdateDto;
import com.homework.transactionservice.transaction.mapper.TransactionMapper;
import com.homework.transactionservice.transaction.model.CursorPage;
import com.homework.transactionservice.transaction.model.Transaction;
import com.homework.transactionservice.transaction.model.TransactionConverter;
import com.homework.transactionservice.transaction.service.TransactionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implement of TransactionService
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    @Transactional
    public Transaction create(TransactionCreateDto transactionCreateDto) {
        // Verify whether the transaction ID exists
        if (exists(Transaction::getTransactionId, transactionCreateDto.getTransactionId())) {
            throw new BusinessException(ErrorCode.TRANSACTION_ALREADY_EXISTS);
        }
        // Insert entity into database
        Transaction transaction = TransactionConverter.toEntity(transactionCreateDto);
        int count = transactionMapper.insert(transaction);
        if (count <= 0) {
            throw new BusinessException(ErrorCode.TRANSACTION_INSERT_FAILED);
        }
        // return the entity with id, create_time, update_time
        return transaction;
    }

    @Override
    @Transactional
    public Transaction delete(Long id) {
        // Verify whether the transaction exists
        Transaction transaction = transactionMapper.selectById(id);
        if (transaction == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_NOT_EXISTS);
        }
        // delete by id
        int count = transactionMapper.deleteById(id);
        if (count <= 0) {
            throw new BusinessException(ErrorCode.TRANSACTION_DELETE_FAILED);
        }
        // return the entity that has been deleted
        return transaction;
    }

    @Override
    @Transactional
    public Transaction update(TransactionUpdateDto transactionUpdateDto) {
        // Verify whether the transaction exists
        boolean exists = exists(Transaction::getId, transactionUpdateDto.getId());
        if (!exists) {
            throw new BusinessException(ErrorCode.TRANSACTION_NOT_EXISTS);
        }

        // update status
        LambdaUpdateWrapper<Transaction> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Transaction::getId, transactionUpdateDto.getId())
                .set(Transaction::getStatus, transactionUpdateDto.getStatus());

        int count = transactionMapper.update(updateWrapper);
        if (count <= 0) {
            throw new BusinessException(ErrorCode.TRANSACTION_UPDATE_FAILED);
        }

        // return the entity that has been updated
        return transactionMapper.selectById(transactionUpdateDto.getId());
    }

    @Override
    @Transactional
    public CursorPage pageQuery(TransactionQueryDto query) {
        // set the filtering conditions
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(query.getAccount()), Transaction::getAccount, query.getAccount());
        wrapper.eq(!StringUtils.isEmpty(query.getType()), Transaction::getType, query.getType());
        wrapper.eq(!StringUtils.isEmpty(query.getStatus()), Transaction::getStatus, query.getStatus());
        wrapper.between(query.getStartTime() != null && query.getEndTime() != null,
                Transaction::getCreateTime, query.getStartTime(), query.getEndTime());

        // If the cursor exists, find the index position through the cursor
        if (query.getCursor() != null) {
            CursorDto cursor = query.getCursor();
            wrapper.and(
                    w -> w.lt(Transaction::getCreateTime, cursor.getLastCreateTime())
                            .or()
                            .eq(Transaction::getCreateTime, cursor.getLastCreateTime())
                            .lt(Transaction::getId, cursor.getLastId())
            );
        }

        // Fixed in reverse order of create time
        wrapper.orderByDesc(Transaction::getCreateTime, Transaction::getId);

        // select one more to determine whether has next page
        Page<Transaction> page = new Page<>(1, query.getPageSize() + 1, false);
        List<Transaction> records = transactionMapper.selectPage(page, wrapper).getRecords();

        // return the paging result
        boolean hasMore = records.size() > query.getPageSize();
        List<Transaction> data = hasMore ? records.subList(0, records.size() - 1) : records;
        return new CursorPage(data, hasMore);
    }

    private boolean exists(SFunction<Transaction, ?> column, Object value) {
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(column, value);
        return transactionMapper.exists(wrapper);
    }
}
