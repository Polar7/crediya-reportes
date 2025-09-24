package co.com.pragma.reports.dynamodb;

import co.com.pragma.reports.model.metriccredit.MetricCredit;
import co.com.pragma.reports.model.metriccredit.gateways.MetricCreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class MetricCreditRepositoryAdapter implements MetricCreditRepository {

    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    private static final String TABLE_NAME = "reporte_aprobados";

    @Override
    public Mono<List<MetricCredit>> findAll() {
        ScanRequest request = ScanRequest.builder()
                .tableName(TABLE_NAME)
                .build();

        return Mono.fromFuture(() -> dynamoDbAsyncClient.scan(request))
                .map(response -> response.items().stream()
                        .map(this::fromItem)
                        .toList()
                );
    }

    @Override
    public Mono<Void> save(BigDecimal amount) {
        return Mono.fromFuture(() ->
                CompletableFuture.allOf(
                        dynamoDbAsyncClient.updateItem(buildUpdateItemRequest(MetricsEnum.CREDITS_APPROVED.getValue(), new BigDecimal(1))),
                        dynamoDbAsyncClient.updateItem(buildUpdateItemRequest(MetricsEnum.TOTAL_AMOUNT_CREDITS_APPROVED.getValue(), amount))
                )
        );
    }

    private UpdateItemRequest buildUpdateItemRequest(String metric, BigDecimal amount) {
        return UpdateItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(Map.of("metrica", AttributeValue.builder().s(metric).build()))
                .updateExpression("ADD #valor :incr")
                .expressionAttributeNames(Map.of("#valor", "valor"))
                .expressionAttributeValues(Map.of(":incr", AttributeValue.builder().n(amount.toPlainString()).build()))
                .build();
    }

    private MetricCredit fromItem(Map<String, AttributeValue> item) {
        return MetricCredit.builder()
                .metric(item.get("metrica").s())
                .value(new BigDecimal(item.get("valor").n()))
                .build();
    }

}
