package co.com.pragma.reports.sqs.listener;

import co.com.pragma.reports.sqs.listener.exception.SQSProcessorException;
import co.com.pragma.reports.usecase.reportmetric.ReportMetricUseCase;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class SQSProcessorMetricCredit implements Function<Message, Mono<Void>> {

    private final ReportMetricUseCase reportMetricUseCase;

    private final Gson gson;

    @Override
    public Mono<Void> apply(Message message) {
        log.info("LISTENER SQSProcessorMetricCredit");
        log.info(message.body());

        return Mono.fromCallable(() -> gson.fromJson(message.body(), MetricCreditApproved.class))
                .flatMap(payload -> reportMetricUseCase.addMetricCreditApproved(payload.amountApproved()))
                .onErrorMap(x -> new SQSProcessorException("Error to listen on SQSProcessorMetricCredit"));
    }

}
