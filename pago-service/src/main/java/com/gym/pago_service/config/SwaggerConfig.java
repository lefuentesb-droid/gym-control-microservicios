package com.gym.pago_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
            new Info()
                .title("API Gestión de Pagos - Gym Control")
                .version("1.0")
                .description("Con esta API se puede administrar los pagos y métodos de pago del gimnasio, incluyendo el registro, actualización y consulta de pagos realizados por los socios.")
        );
    }
}
