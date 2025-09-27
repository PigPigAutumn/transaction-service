package com.homework.transactionservice.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisPlusConfig
 *
 * @author Jiaqi HUANG
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Configuration
@MapperScan(basePackages = {
        "com.homework.transactionservice.transaction.mapper"
})
public class MyBatisPlusConfig {

    /**
     * register a DateTimeMetaObjectHandler instance
     *
     * @return dateTimeMetaObjectHandler
     */
    @Bean
    public DateTimeMetaObjectHandler dateTimeMetaObjectHandler() {
        return new DateTimeMetaObjectHandler();
    }

    /**
     * register pagination interceptor
     *
     * @return pagination interceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }
}
