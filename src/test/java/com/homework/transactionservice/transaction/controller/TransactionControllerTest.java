package com.homework.transactionservice.transaction.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homework.transactionservice.TransactionServiceApplicationTests;
import com.homework.transactionservice.exception.enums.ErrorCode;
import com.homework.transactionservice.transaction.dto.TransactionCreateDto;
import com.homework.transactionservice.transaction.dto.TransactionQueryDto;
import com.homework.transactionservice.transaction.dto.TransactionUpdateDto;
import com.homework.transactionservice.transaction.model.Transaction;
import com.homework.transactionservice.transaction.mapper.TransactionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * TransactionController Testing
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@SpringBootTest(classes = TransactionServiceApplicationTests.class)
@AutoConfigureMockMvc
@Transactional
@Sql("/sql/data.sql")
@Rollback
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    private TransactionCreateDto validCreateDto;
    private TransactionUpdateDto validUpdateDto;
    private TransactionQueryDto validQueryDto;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    void setUp() {
        // Make sure MockMvc uses the complete Spring context, including the global exception handler
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Prepare a DTO to create transaction
        validCreateDto = new TransactionCreateDto();
        validCreateDto.setTransactionId("TXN202505001");
        validCreateDto.setAccount("ACC001");
        validCreateDto.setAmount(new BigDecimal("100.00"));
        validCreateDto.setBalance(new BigDecimal("900.50"));
        validCreateDto.setType("DEPOSIT");
        validCreateDto.setStatus("COMPLETED");
        validCreateDto.setCounterparty("Bank of China");
        validCreateDto.setDescription("Test deposit");

        // Prepare a DTO to update transaction
        validUpdateDto = new TransactionUpdateDto();
        validUpdateDto.setId(6L);
        validUpdateDto.setStatus("COMPLETED");

        // Prepare a DTO to query transaction list
        validQueryDto = new TransactionQueryDto();
        validQueryDto.setAccount("ACC001");
        validQueryDto.setPageSize(10);
    }

    @Test
    void create_WithValidData_ReturnsSuccessResult() throws Exception {
        String requestContent = objectMapper.writeValueAsString(validCreateDto);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.transactionId").value("TXN202505001"));

        // Verify that the data has been saved to the database by using the selectOne method of BaseMapper
        LambdaQueryWrapper<Transaction> queryWrapper = Wrappers.lambdaQuery(Transaction.class)
                .eq(Transaction::getTransactionId, "TXN202505001");
        Transaction savedTransaction = transactionMapper.selectOne(queryWrapper);

        assertNotNull(savedTransaction);
        assertEquals("ACC001", savedTransaction.getAccount());
        assertEquals("TXN202505001", savedTransaction.getTransactionId());
    }

    @Test
    void create_WithDuplicateTransactionId_ReturnsError() throws Exception {
        // Prepare a duplicated transaction id
        TransactionCreateDto duplicateCreateDto = new TransactionCreateDto();
        duplicateCreateDto.setTransactionId("TXN202501001");
        duplicateCreateDto.setAccount("ACC001");
        duplicateCreateDto.setAmount(new BigDecimal("100.00"));
        duplicateCreateDto.setBalance(new BigDecimal("900.50"));
        duplicateCreateDto.setType("DEPOSIT");
        duplicateCreateDto.setStatus("COMPLETED");
        duplicateCreateDto.setCounterparty("Bank of China");
        duplicateCreateDto.setDescription("Duplicate test transaction");

        String requestContent = objectMapper.writeValueAsString(duplicateCreateDto);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ErrorCode.TRANSACTION_ALREADY_EXISTS.getCode()))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void create_WithoutTransactionId_ReturnsError() throws Exception {
        TransactionCreateDto duplicateCreateDto = new TransactionCreateDto();
        String requestContent = objectMapper.writeValueAsString(duplicateCreateDto);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ErrorCode.VALIDATION_FAILED.getCode()))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void delete_WithValidId_ReturnsSuccessResult() throws Exception {
        // Ensure that the transaction with ID 1 exists by using the selectById method of BaseMapper
        Transaction existingTransaction = transactionMapper.selectById(1L);
        assertNotNull(existingTransaction);
        assertEquals("TXN202501001", existingTransaction.getTransactionId());

        mockMvc.perform(delete("/transaction/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.id").value(1));

        // Verify that the data has been deleted from the database by using the selectById method of BaseMapper
        Transaction deletedTransaction = transactionMapper.selectById(1L);
        assertNull(deletedTransaction);
    }

    @Test
    void delete_WithNonExistentId_ReturnsError() throws Exception {
        // Make sure that the transaction with ID 999 does not exist by using the selectById method of BaseMapper
        Transaction nonExistentTransaction = transactionMapper.selectById(999L);
        assertNull(nonExistentTransaction);

        mockMvc.perform(delete("/transaction/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ErrorCode.TRANSACTION_NOT_EXISTS.getCode()))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void update_WithValidData_ReturnsSuccessResult() throws Exception {
        // Ensure that the transaction with ID 6 exists and its status is "PENDING".
        Transaction existingTransaction = transactionMapper.selectById(6L);
        assertNotNull(existingTransaction);
        assertEquals("PENDING", existingTransaction.getStatus());

        String requestContent = objectMapper.writeValueAsString(validUpdateDto);

        mockMvc.perform(put("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.id").value(6))
                .andExpect(jsonPath("$.data.status").value("COMPLETED"));

        // Verify that the data has been updated to the database by using the selectById method of BaseMapper
        Transaction updatedTransaction = transactionMapper.selectById(6L);
        assertNotNull(updatedTransaction);
        assertEquals("COMPLETED", updatedTransaction.getStatus());
    }

    @Test
    void update_WithNonExistentId_ReturnsError() throws Exception {
        // Prepare to update the non-existent ID
        TransactionUpdateDto updateDto = new TransactionUpdateDto();
        updateDto.setId(999L);
        updateDto.setStatus("COMPLETED");

        String requestContent = objectMapper.writeValueAsString(updateDto);

        mockMvc.perform(put("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ErrorCode.TRANSACTION_NOT_EXISTS.getCode()))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void pageQuery_WithValidData_ReturnsPageResult() throws Exception {
        String requestContent = objectMapper.writeValueAsString(validQueryDto);

        mockMvc.perform(post("/transaction/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(5))
                .andExpect(jsonPath("$.data[0].transactionId").value("TXN202504003"))
                .andExpect(jsonPath("$.cursor.hasMore").value(false));
    }

    @Test
    void pageQuery_WithTypeFilter_ReturnsFilteredResults() throws Exception {
        TransactionQueryDto queryDto = new TransactionQueryDto();
        queryDto.setAccount("ACC001");
        queryDto.setType("WITHDRAWAL");
        queryDto.setPageSize(10);

        String requestContent = objectMapper.writeValueAsString(queryDto);

        mockMvc.perform(post("/transaction/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].transactionId").value("TXN202503003"))
                .andExpect(jsonPath("$.cursor.hasMore").value(false));
    }

    @Test
    void pageQuery_WithStatusFilter_ReturnsFilteredResults() throws Exception {
        TransactionQueryDto queryDto = new TransactionQueryDto();
        queryDto.setAccount("ACC001");
        queryDto.setStatus("PENDING");
        queryDto.setPageSize(10);

        String requestContent = objectMapper.writeValueAsString(queryDto);

        mockMvc.perform(post("/transaction/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].transactionId").value("TXN202504003"))
                .andExpect(jsonPath("$.cursor.hasMore").value(false));
    }

    @Test
    void pageQuery_WithTimeRange_ReturnsFilteredResults() throws Exception {
        TransactionQueryDto queryDto = new TransactionQueryDto();
        queryDto.setAccount("ACC001");
        queryDto.setStartTime(LocalDateTime.parse("2025-02-01 00:00:00", formatter));
        queryDto.setEndTime(LocalDateTime.parse("2025-03-31 23:59:59", formatter));
        queryDto.setPageSize(10);

        String requestContent = objectMapper.writeValueAsString(queryDto);

        mockMvc.perform(post("/transaction/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].transactionId").value("TXN202503003"))
                .andExpect(jsonPath("$.cursor.hasMore").value(false));
    }

    @Test
    void selectList_UsingBaseMapper() throws Exception {
        // Use the selectList method of BaseMapper to query the transactions of a specific account
        LambdaQueryWrapper<Transaction> queryWrapper = Wrappers.lambdaQuery(Transaction.class)
                .eq(Transaction::getAccount, "ACC001")
                .orderByDesc(Transaction::getCreateTime);
        List<Transaction> transactions = transactionMapper.selectList(queryWrapper);

        assertEquals(5, transactions.size());
        assertEquals("TXN202504003", transactions.get(0).getTransactionId());

        TransactionQueryDto queryDto = new TransactionQueryDto();
        queryDto.setAccount("ACC001");
        queryDto.setPageSize(10);

        String requestContent = objectMapper.writeValueAsString(queryDto);
        mockMvc.perform(post("/transaction/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(5))
                .andExpect(jsonPath("$.data[0].transactionId").value("TXN202504003"));
    }

    @Test
    void selectPage_UsingBaseMapper() throws Exception {
        // Use the selectPage method of BaseMapper for pagination queries
        Page<Transaction> page = new Page<>(1, 3);
        LambdaQueryWrapper<Transaction> queryWrapper = Wrappers.lambdaQuery(Transaction.class)
                .eq(Transaction::getAccount, "ACC001")
                .orderByDesc(Transaction::getCreateTime);
        IPage<Transaction> resultPage = transactionMapper.selectPage(page, queryWrapper);

        assertEquals(3, resultPage.getRecords().size());
        assertEquals(5, resultPage.getTotal());
        assertEquals("TXN202504003", resultPage.getRecords().get(0).getTransactionId());

        TransactionQueryDto queryDto = new TransactionQueryDto();
        queryDto.setAccount("ACC001");
        queryDto.setPageSize(3);

        String requestContent = objectMapper.writeValueAsString(queryDto);
        mockMvc.perform(post("/transaction/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(3))
                .andExpect(jsonPath("$.data[0].transactionId").value("TXN202504003"))
                .andExpect(jsonPath("$.cursor.hasMore").value(true));
    }

    @Test
    void update_UsingBaseMapper() throws Exception {
        // Update the record using the update method of BaseMapper
        LambdaUpdateWrapper<Transaction> updateWrapper = Wrappers.lambdaUpdate(Transaction.class)
                .eq(Transaction::getId, 6L)
                .set(Transaction::getStatus, "COMPLETED");
        int updateCount = transactionMapper.update(null, updateWrapper);

        assertEquals(1, updateCount);

        Transaction updatedTransaction = transactionMapper.selectById(6L);
        assertNotNull(updatedTransaction);
        assertEquals("COMPLETED", updatedTransaction.getStatus());

        TransactionUpdateDto updateDto = new TransactionUpdateDto();
        updateDto.setId(6L);
        updateDto.setStatus("PENDING");

        String requestContent = objectMapper.writeValueAsString(updateDto);
        mockMvc.perform(put("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("PENDING"));
    }

    @Test
    void delete_UsingBaseMapper() throws Exception {
        // Use the deleteById method of BaseMapper to delete the record
        int deleteCount = transactionMapper.deleteById(1L);

        assertEquals(1, deleteCount);

        Transaction deletedTransaction = transactionMapper.selectById(1L);
        assertNull(deletedTransaction);

        mockMvc.perform(delete("/transaction/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(2));

        Transaction deletedTransaction2 = transactionMapper.selectById(2L);
        assertNull(deletedTransaction2);
    }
}