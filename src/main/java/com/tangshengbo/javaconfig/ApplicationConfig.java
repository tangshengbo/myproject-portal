package com.tangshengbo.javaconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Tangshengbo on 2018/1/17.
 */
@Configuration
//@ComponentScan(basePackages = {"com.tangshengbo.service", "com.tangshengbo.dao"})
@ImportResource(value = {"classpath:spring-context.xml"})
public class ApplicationConfig {

//    @Bean
//    public DataSource dataSource(@Value("${jdbc_url_log}") String url,
//                                 @Value("${jdbc_username}") String username,
//                                 @Value("${jdbc_password}") String password,
//                                 @Value("${driverClassNameLog}") String driver) {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        dataSource.setDriverClassName(driver);
//        return dataSource;
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        //添加XML目录
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapping/*.xml"));
//            return sqlSessionFactoryBean.getObject();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        mapperScannerConfigurer.setBasePackage("com.tangshengbo.dao");
//        return mapperScannerConfigurer;
//    }
//
//    @Bean
//    public PlatformTransactionManager annotationDrivenTransactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
}
