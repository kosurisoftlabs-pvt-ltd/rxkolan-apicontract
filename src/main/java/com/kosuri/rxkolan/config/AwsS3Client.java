package com.kosuri.rxkolan.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AwsS3Client {

    private final AppProperties appProperties;

    @Bean
    public AmazonS3 amazonS3Client() {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(appProperties.getAwsConfig().getAccessKey(),
                appProperties.getAwsConfig().getSecretKey());

        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(appProperties.getAwsConfig().getBucketRegion()).build();
    }
}