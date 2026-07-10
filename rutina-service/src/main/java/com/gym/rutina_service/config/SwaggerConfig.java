package com.gym.rutina_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${gateway.url:http://localhost:8080}")
    private String gatewayUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Rutinas y Ejercicios")
                        .version("1.0")
                        .description("Microservicio encargado de gestionar rutinas y ejercicios"));
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
