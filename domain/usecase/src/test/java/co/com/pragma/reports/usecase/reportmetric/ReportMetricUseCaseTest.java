package co.com.pragma.reports.usecase.reportmetric;

import co.com.pragma.reports.model.metriccredit.MetricCredit;
import co.com.pragma.reports.model.metriccredit.gateways.MetricCreditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportMetricUseCaseTest {

    @Mock
    private MetricCreditRepository metricCreditRepository;

    @InjectMocks
    private ReportMetricUseCase reportMetricUseCase;

    @Test
    void findAllMetrics_shouldReturnListOfMetrics() {
        MetricCredit metric1 = new MetricCredit("m1", new BigDecimal("1000")); // Assuming MetricCredit is available
        MetricCredit metric2 = new MetricCredit("m2", new BigDecimal("2000"));
        List<MetricCredit> expectedList = List.of(metric1, metric2);

        when(metricCreditRepository.findAll()).thenReturn(Mono.just(expectedList));

        StepVerifier.create(reportMetricUseCase.findAllMetrics())
                .expectNext(expectedList)
                .verifyComplete();

        verify(metricCreditRepository, times(1)).findAll();
    }

    @Test
    void findAllMetrics_shouldReturnEmptyListWhenNoMetricsExist() {
        List<MetricCredit> expectedEmptyList = Collections.emptyList();

        when(metricCreditRepository.findAll()).thenReturn(Mono.just(expectedEmptyList));

        StepVerifier.create(reportMetricUseCase.findAllMetrics())
                .expectNext(expectedEmptyList)
                .verifyComplete();
    }

    @Test
    void addMetricCreditApproved_shouldCallRepositorySaveAndComplete() {
        BigDecimal amount = new BigDecimal("50000.50");

        when(metricCreditRepository.save(amount)).thenReturn(Mono.empty());

        StepVerifier.create(reportMetricUseCase.addMetricCreditApproved(amount))
                .verifyComplete();

        verify(metricCreditRepository, times(1)).save(amount);
    }

}