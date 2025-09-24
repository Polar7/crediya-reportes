package co.com.pragma.reports.dynamodb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Configuration
public class DynamoDBConfig {

    @Bean()
    public DynamoDbAsyncClient dynamoDbClient(@Value("${aws.dynamodb.region}") String regionDynamo) {
        return DynamoDbAsyncClient.builder()
                .region(Region.of(regionDynamo))
                .credentialsProvider(getProviderChain())
                .build();
    }

    private AwsCredentialsProviderChain getProviderChain() {
        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .addCredentialsProvider(SystemPropertyCredentialsProvider.create())
                .addCredentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .addCredentialsProvider(ProfileCredentialsProvider.create())
                .addCredentialsProvider(ContainerCredentialsProvider.builder().build())
                .addCredentialsProvider(InstanceProfileCredentialsProvider.create())
                .build();
    }

}
