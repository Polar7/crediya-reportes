package co.com.pragma.reports.api;

import co.com.pragma.reports.model.metriccredit.MetricCredit;
import co.com.pragma.reports.usecase.reportmetric.ReportMetricUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final ReportMetricUseCase reportMetricUseCase;

    @Operation(
            operationId = "getAllMetrics",
            summary = "Obtiene todas las métricas",
            description = "Retorna la lista completa de métricas almacenadas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de métricas retornada con éxito",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MetricCredit.class)))
                    )
            }
    )
    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        return reportMetricUseCase.findAllMetrics()
                .flatMap(metrics -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(metrics)
                )
                .switchIfEmpty(ServerResponse.noContent().build());
    }

}
