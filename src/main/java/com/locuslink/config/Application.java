 package com.locuslink.config;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.DecryptionFailureException;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InternalServiceErrorException;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;



@ComponentScan("com.locuslink")
  @EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class,
   DataSourceTransactionManagerAutoConfiguration.class,
  HibernateJpaAutoConfiguration.class})
@SpringBootApplication
public class Application extends SpringBootServletInitializer   {

	private static final Logger logger = Logger.getLogger(Application.class);

	@Autowired
	private AwsSecrets awsSecrets;

	// 12-21-2022 TODO Add this back C.Sparks ??
	//@Value("${app.secretName}")
	//private  String appSecretName;

	@Value("${app.region}")
	private String appRegion;

	// 2-28-2022
	@Value("${aws.accessKeyId}")
	private String accessKeyId;

	@Value("${aws.secretKey}")
	private String secretKey;


    @PostConstruct
	public void init(){
	    // Setting Spring Boot SetTimeZone
	    TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
	}



	// 5-6-2022 testing to thymeleaf can use the sec:authorize
	//  This was the magic bullet to making the thymeleaf sec:authorize work
	@Bean
	public SpringSecurityDialect securityDialect() {
	    return new SpringSecurityDialect();
	}



	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// Forward to starting index page
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/").setViewName("forward:/index.html");
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }



// 12-12-2022 add later  c.sparks
//	@Bean(name="secret")
//	public AwsSecrets  getAwsSecrets() {
//
//
//		try {
//			System.setProperty("aws.accessKeyId", accessKeyId);
//			System.setProperty("aws.secretKey", secretKey);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// Added to the application context, so it can be Autowired
//	    DefaultListableBeanFactory context = new DefaultListableBeanFactory();
//	    GenericBeanDefinition beanOtherDef = new GenericBeanDefinition();
//	    beanOtherDef.setBeanClass(AwsSecrets.class);
//	    context.registerBeanDefinition("AwsSecrets", beanOtherDef);
//
//	    AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
//	                                     .withRegion(appRegion)
//	                                     .build();
//
//	    GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(appSecretName);
//	    GetSecretValueResult getSecretValueResult = null;
//	    try {
//	        getSecretValueResult = client.getSecretValue(getSecretValueRequest);
//	    } catch (DecryptionFailureException e) {
//	        throw e;
//	    } catch (InternalServiceErrorException e) {
//	         throw e;
//	    } catch (InvalidParameterException e) {
//	        throw e;
//	    } catch (InvalidRequestException e) {
//	        throw e;
//	    } catch (ResourceNotFoundException e) {
//	        throw e;
//	    }
//
//	    if (getSecretValueResult.getSecretString() != null) {
//	    	awsSecrets.setDbUserName(getSecretValueResult.getName());
//	    	awsSecrets.setDbPassword(getSecretValueResult.getSecretString());
//	    }
//
//	    return awsSecrets;
//	 }





}