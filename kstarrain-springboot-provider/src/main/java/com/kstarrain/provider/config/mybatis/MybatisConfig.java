package com.kstarrain.provider.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author: Dong Yu
 * @create: 2020-04-22 09:18
 * @description:
 */
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class, MybatisProperties.class})
public class MybatisConfig {

    @Autowired
    private DataSourceProperties dataSourceProperties;


    // 表示这个数据源是默认数据源
    @Primary
    @Bean
    public DataSource primaryDataSource() {
        DruidDataSource mybatisDataSource = new DruidDataSource();
        mybatisDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        mybatisDataSource.setUrl(dataSourceProperties.getUrl());
        mybatisDataSource.setUsername(dataSourceProperties.getUsername());
        mybatisDataSource.setPassword(dataSourceProperties.getPassword());
        return mybatisDataSource;
    }

    @Primary
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        return factory.getObject();
    }

    @Primary
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }



}
