package com.noreabang.strawberryrabbit.infra.amazon

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class S3Config {
    @Value("\${CLOUD_AWS_ACCESS_KEY}")
    private lateinit var accessKey: String

    @Value("\${CLOUD_AWS_SECRET_KEY}")
    private lateinit var secretKey: String

    @Value("\${CLOUD_AWS_REGION_STATIC}")
    private lateinit var region: String

    @Bean
    fun amazonS3Client(): AmazonS3Client {
        val awsCredentials = BasicAWSCredentials(accessKey, secretKey)
        return AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .build() as AmazonS3Client
    }
}