package com.homework.transactionservice.transaction.model;

import com.homework.transactionservice.transaction.dto.TransactionCreateDto;
import com.homework.transactionservice.transaction.vo.TransactionVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter used to transfer between Entity, DTO, VO
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class TransactionConverter {

    /**
     * DTO to Entity
     *
     * @param transactionCreateDto the DTO
     * @return Entity
     */
    public static Transaction toEntity(TransactionCreateDto transactionCreateDto) {
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(transactionCreateDto, transaction);
        return transaction;
    }

    /**
     * Entity to VO
     *
     * @param transaction the Entity
     * @return VO
     */
    public static TransactionVO toVO(Transaction transaction) {
        TransactionVO transactionVO = new TransactionVO();
        BeanUtils.copyProperties(transaction, transactionVO);
        return transactionVO;
    }

    /**
     * Entity list to VO list
     *
     * @param transactionList the entity list
     * @return VO list
     */
    public static List<TransactionVO> toVOList(List<Transaction> transactionList) {
        return transactionList.stream().map(TransactionConverter::toVO).collect(Collectors.toList());
    }
}
