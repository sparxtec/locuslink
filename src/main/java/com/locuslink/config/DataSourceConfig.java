package com.locuslink.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan(basePackages = {"com.locuslink.model"})
@EnableTransactionManagement
//@DependsOn("secret")
public class DataSourceConfig {

	@Autowired
	private Environment env;

	@Autowired
	private AwsSecrets awsSecrets;


    @Primary
    @Bean(name = "locuslinkDataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource getlocuslinkDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(awsSecrets.getDbUserName());
        dataSource.setPassword(awsSecrets.getDbPassword());

        System.out.println("## locuslinkDataSource Created : " + dataSource);
        return dataSource;
    }


    @Autowired
    @Primary
    @Bean(name = "locuslinkSessionFactory")
    public SessionFactory getlocuslinkSessionFactory(DataSource dataSource) throws Exception {

    	Properties properties = new Properties();
    	properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put("hibernate.default_schema", env.getProperty("spring.jpa.properties.hibernate.default_schema"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
        properties.put("current_session_context_class", env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
 
        // 1-6-2022 This works, none of the other config settings in the property file work, to make vaslidation happen during startup.
        properties.put("hibernate.hbm2ddl.auto", "validate");
        
        
        
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setPackagesToScan(new String[] { "com.locuslink" });
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(properties);
        factoryBean.afterPropertiesSet();

        SessionFactory sf = factoryBean.getObject();
        

        System.out.println("## locuslinkSessionFactory Created: " + sf);
        return sf;
    }



    @Primary
    @Autowired
    @Bean(name = "locuslinkTransactionManager")
    @Qualifier("locuslinkDataSource")
    public PlatformTransactionManager getTransactionManager(SessionFactory sessionFactory) {
    	JpaTransactionManager transactionManager = new JpaTransactionManager(sessionFactory);
        transactionManager.setDataSource(getlocuslinkDataSource());
        
        System.out.println("## locuslinkTransactionManager Created: " + transactionManager);
        
        return transactionManager;
    }

//    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
//    	HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
//        transactionManager.setDataSource(getlocuslinkDataSource());
//        
//        System.out.println("## locuslinkTransactionManager Created: " + transactionManager);
//        
//        return transactionManager;
//    }
    
}
