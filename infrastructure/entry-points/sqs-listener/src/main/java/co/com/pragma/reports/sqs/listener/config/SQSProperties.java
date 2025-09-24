package co.com.pragma.reports.sqs.listener.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "entrypoint.sqs")
public record SQSProperties(
        String region,
        String endpoint,
        String queueMetricCreditApprovedUrl,
        int waitTimeSeconds,
        int visibilityTimeoutSeconds,
        int maxNumberOfMessages,
        int numberOfThreads) {
}
