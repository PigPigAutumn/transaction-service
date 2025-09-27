package com.homework.transactionservice.transaction.service.impl;

import com.homework.transactionservice.TransactionServiceApplicationTests;
import com.homework.transactionservice.exception.BusinessException;
import com.homework.transactionservice.exception.enums.ErrorCode;
import com.homework.transactionservice.transaction.dto.CursorDto;
import com.homework.transactionservice.transaction.dto.TransactionCreateDto;
import com.homework.transactionservice.transaction.dto.TransactionQueryDto;
import com.homework.transactionservice.transaction.dto.TransactionUpdateDto;
import com.homework.transactionservice.transaction.model.CursorPage;
import com.homework.transactionservice.transaction.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TransactionServiceImpl Testing
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@SpringBootTest(classes = TransactionServiceApplicationTests.class)
@Transactional
@Sql("/sql/data.sql")
@Rollback
public class TransactionServiceImplTest {

    @Autowired
    private TransactionServiceImpl transactionService;

    @Test
    void testCreateTransaction_Success() {
        TransactionCreateDto dto = new TransactionCreateDto();
        dto.setTransactionId("TXN_TEST_001");
        dto.setAccount("ACC_TEST_001");
        dto.setAmount(new BigDecimal("100.00"));
        dto.setBalance(new BigDecimal("500.00"));
        dto.setType("DEPOSIT");
        dto.setStatus("PENDING");
        dto.setCounterparty("Test Bank");
        dto.setDescription("Test transaction");

        Transaction result = transactionService.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("TXN_TEST_001", result.getTransactionId());
        assertEquals("ACC_TEST_001", result.getAccount());
        assertEquals("PENDING", result.getStatus());
    }

    @Test
    void testCreateTransaction_DuplicateTransactionId() {
        TransactionCreateDto dto = new TransactionCreateDto();
        dto.setTransactionId("TXN202501001");
        dto.setAccount("ACC_TEST_001");
        dto.setAmount(new BigDecimal("100.00"));
        dto.setBalance(new BigDecimal("500.00"));
        dto.setType("DEPOSIT");
        dto.setStatus("PENDING");
        dto.setCounterparty("Test Bank");
        dto.setDescription("Test transaction");

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            transactionService.create(dto);
        });

        assertEquals(ErrorCode.TRANSACTION_ALREADY_EXISTS.getCode(), exception.getCode());
    }

    @Test
    void testDeleteTransaction_Success() {
        // Delete the records existing in data.sql
        Transaction result = transactionService.delete(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("TXN202501001", result.getTransactionId());
    }

    @Test
    void testDeleteTransaction_NotFound() {
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            transactionService.delete(999L);
        });

        assertEquals(ErrorCode.TRANSACTION_NOT_EXISTS.getCode(), exception.getCode());
    }

    @Test
    void testUpdateTransaction_Success() {
        TransactionUpdateDto dto = new TransactionUpdateDto();
        dto.setId(3L);
        dto.setStatus("COMPLETED");

        Transaction result = transactionService.update(dto);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("COMPLETED", result.getStatus());
    }

    @Test
    void testUpdateTransaction_NotFound() {
        TransactionUpdateDto dto = new TransactionUpdateDto();
        dto.setId(999L);
        dto.setStatus("COMPLETED");

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            transactionService.update(dto);
        });

        assertEquals(ErrorCode.TRANSACTION_NOT_EXISTS.getCode(), exception.getCode());
    }

    @Test
    void testPageQuery_NoFilters() {
        TransactionQueryDto query = new TransactionQueryDto();
        query.setPageSize(4);

        CursorPage page = transactionService.pageQuery(query);

        assertNotNull(page);
        assertEquals(4, page.getData().size());
        assertTrue(page.isHasMore());
        assertNotNull(page.getLastId());
        assertNotNull(page.getLastCreateTime());

        Long[] ids = new Long[]{12L, 11L, 10L, 9L};
        List<Transaction> data = page.getData();
        for (int i = 0; i < page.getData().size(); i++) {
            assertEquals(ids[i], data.get(i).getId());
        }
    }

    @Test
    void testPageQuery_WithScrolling() {
        TransactionQueryDto query = new TransactionQueryDto();
        query.setPageSize(4);

        int total = 0;
        CursorPage page;
        do {
            page = transactionService.pageQuery(query);
            total += page.getData().size();

            List<Transaction> data = page.getData();
            Transaction last = data.get(data.size() - 1);

            CursorDto cursorDto = new CursorDto(last.getId(), last.getCreateTime());
            query.setCursor(cursorDto);
        } while (page.isHasMore());

        assertEquals(12, total);
    }

    @Test
    void testPageQuery_WithAccountFilter() {
        TransactionQueryDto query = new TransactionQueryDto();
        query.setAccount("ACC001");
        query.setPageSize(10);

        CursorPage page = transactionService.pageQuery(query);

        assertNotNull(page);
        assertEquals(5, page.getData().size());
    }

    @Test
    void testPageQuery_WithStatusFilter() {
        TransactionQueryDto query = new TransactionQueryDto();
        query.setStatus("COMPLETED");
        query.setPageSize(10);

        CursorPage page = transactionService.pageQuery(query);

        assertNotNull(page);
        assertEquals(10, page.getData().size());
    }

    @Test
    void testPageQuery_WithTimeRange() {
        TransactionQueryDto query = new TransactionQueryDto();
        query.setStartTime(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
        query.setEndTime(LocalDateTime.of(2025, 1, 31, 23, 59, 59));
        query.setPageSize(10);

        CursorPage page = transactionService.pageQuery(query);

        assertNotNull(page);
        assertEquals(3, page.getData().size());
    }
}