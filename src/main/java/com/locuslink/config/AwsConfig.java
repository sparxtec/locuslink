package com.locuslink.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsConfig  {


	// 2-28-2022
	@Value("${aws.accessKeyId}")
	private String accessKeyId;

	@Value("${aws.secretKey}")
	private String secretKey;

	@Bean
	public AmazonS3Client amazonS3Client( ) {

//		//AWSCredentials credentials = new BasicAWSCredentials("AKIAYEKIDF43ETDD4BFA", "yX5Obex+xSJXa9yJ5ENdMZL6lOfH0EAkyu8XQ3D9");
//		AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretKey);
//
//		AmazonS3Client s3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
//        		.withCredentials(new AWSStaticCredentialsProvider(credentials))
//        		.withRegion(Regions.US_EAST_1)
//        		.build();
//
//		return (AmazonS3Client) s3Client;

		return null;
    }
}