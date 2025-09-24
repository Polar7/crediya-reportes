package co.com.pragma.reports.usecase.reportmetric;

import co.com.pragma.reports.model.metriccredit.MetricCredit;
import co.com.pragma.reports.model.metriccredit.gateways.MetricCreditRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class ReportMetricUseCase {

    private final MetricCreditRepository metricCreditRepository;

    public Mono<List<MetricCredit>> findAllMetrics() {
        return metricCreditRepository.findAll();
    }

    public Mono<Void> addMetricCreditApproved(BigDecimal amountCreditApproved) {
        return metricCreditRepository.save(amountCreditApproved).then();
    }

}
