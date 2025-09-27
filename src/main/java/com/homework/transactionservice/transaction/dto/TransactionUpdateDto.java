package com.homework.transactionservice.transaction.dto;

import com.homework.transactionservice.validation.PatternRegex;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * TransactionUpdateDto
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Getter
@Setter
public class TransactionUpdateDto {

    @NotNull
    private Long id;

    @NotEmpty
    @Pattern(regexp = PatternRegex.COMMON_REGEX_16)
    private String status;
}