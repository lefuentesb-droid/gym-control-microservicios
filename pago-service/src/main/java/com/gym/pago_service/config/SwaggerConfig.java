package com.gym.pago_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springdoc.core.customizers.OpenApiCustomizer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Value("${gateway.url:http://localhost:8080}")
    private String gatewayUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
            new Info()
                .title("API Gestión de Pagos - Gym Control")
                .version("1.0")
                .description("Con esta API se puede administrar los pagos y métodos de pago del gimnasio, incluyendo el registro, actualización y consulta de pagos realizados por los socios.")
        );
    }

    @Bean
    public OpenApiCustomizer gatewayServerCustomizer() {
        return openApi -> {
            openApi.getServers().clear();
            openApi.addServersItem(
                new Server()
                    .url(gatewayUrl)
                    .description("API Gateway (Eureka load-balanced)")
            );
        };
    }
}
