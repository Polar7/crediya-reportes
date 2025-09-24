package co.com.pragma.reports.api;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(beanClass = Handler.class, beanMethod = "listenGETUseCase")
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/v1/metricas"), handler::listenGETUseCase);
    }

}
