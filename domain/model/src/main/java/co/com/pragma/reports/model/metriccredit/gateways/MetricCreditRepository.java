package co.com.pragma.reports.model.metriccredit.gateways;

import co.com.pragma.reports.model.metriccredit.MetricCredit;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

public interface MetricCreditRepository {

    Mono<List<MetricCredit>> findAll();

    Mono<Void> save(BigDecimal amount);

}
