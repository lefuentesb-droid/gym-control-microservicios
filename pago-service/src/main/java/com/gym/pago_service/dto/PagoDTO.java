package com.gym.pago_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PagoDTO {

    private Integer id;

    @NotNull(message = "El monto es obligatorio")
    @Min(value = 1, message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDate fechaPago;

    @NotBlank(message = "El comprobante es obligatorio")
    @Size(min = 5, max = 50, message = "El comprobante debe tener entre 5 y 50 caracteres")
    private String comprobante;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    // Vienen de socio-service
    @NotNull(message = "El id del socio es obligatorio")
    @Min(value = 1, message = "El id del socio debe ser mayor a 0")
    private Integer idSocio;

    @NotNull(message = "El id de la membresía es obligatorio")
    @Min(value = 1, message = "El id de la membresía debe ser mayor a 0")
    private Integer idMembresia;

    // Vive en este microservicio
    @NotNull(message = "El id del método de pago es obligatorio")
    @Min(value = 1, message = "El id del método de pago debe ser mayor a 0")
    private Integer idMetodoPago;

    private String nombreMetodoPago;

    // Se completa con la llamada REST a socio-service (WebClient)
    private String nombreSocio;
}
