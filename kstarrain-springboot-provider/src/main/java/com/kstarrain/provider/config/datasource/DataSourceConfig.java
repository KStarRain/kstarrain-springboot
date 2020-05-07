package com.kstarrain.provider.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author: Dong Yu
 * @create: 2019-12-02 14:53
 * @description:
 */
@Configuration
public class DataSourceConfig {


    // 主数据源
    @Primary
    @Bean("primaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.druid")
    public DataSource primaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }


    // Elastic Job 事件记录数据源
    @Bean("jobEventDataSource")
    @ConfigurationProperties(prefix="spring.elasticjob.event.datasource")
    public DataSource jobEventDataSource() {
        return DruidDataSourceBuilder.create().build();
    }



}
