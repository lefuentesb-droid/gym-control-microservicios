package com.gym.pago_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoDTO {

    private Integer id;

    @NotNull(message = "El monto es obligatorio")
    private Double monto;

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDate fechaPago;

    @NotBlank(message = "El comprobante es obligatorio")
    private String comprobante;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    // Vienen de socio-service
    @NotNull(message = "El id del socio es obligatorio")
    private Integer idSocio;

    @NotNull(message = "El id de la membresía es obligatorio")
    private Integer idMembresia;

    // Vive en este microservicio
    @NotNull(message = "El id del método de pago es obligatorio")
    private Integer idMetodoPago;

    private String nombreMetodoPago;

    // Se completa con la llamada REST a socio-service (WebClient)
    private String nombreSocio;
}
