package com.kstarrain.provider.config.elasticjob;

import com.alibaba.druid.pool.DruidDataSource;
import com.kstarrain.provider.config.elasticjob.properties.JobEventDataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author: Dong Yu
 * @create: 2019-12-02 14:53
 * @description:
 */
@Configuration
@EnableConfigurationProperties(JobEventDataSourceProperties.class)
public class JobEventDataSourceConfig {

    @Autowired
    private JobEventDataSourceProperties jobEventDataSourceProperties;


    @Bean
    public DataSource jobEventDataSource() {
        DruidDataSource jobEventDataSource = new DruidDataSource();
        jobEventDataSource.setDriverClassName(jobEventDataSourceProperties.getDriver());
        jobEventDataSource.setUrl(jobEventDataSourceProperties.getUrl());
        jobEventDataSource.setUsername(jobEventDataSourceProperties.getUsername());
        jobEventDataSource.setPassword(jobEventDataSourceProperties.getPassword());
        return jobEventDataSource;
    }



}
