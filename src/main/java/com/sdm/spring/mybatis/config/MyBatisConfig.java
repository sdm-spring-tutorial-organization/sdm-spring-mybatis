package com.sdm.spring.mybatis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    @Autowired
    DataSource dataSource;

    /**
     * SqlSessionFactory 설정
     * [과정] SqlSessionFactoryBean (dataSource & location) > SqlSessionFactoryBuilder > MyBatis Config File > SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {

        // datasource 연동
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        // mapper-location 연동
        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:db/mappers/*-mapper.xml");
        sqlSessionFactoryBean.setMapperLocations(res);

        return sqlSessionFactoryBean.getObject(); /* Spring - FactoryBean */
    }

    /**
     * Transaction 설정
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
