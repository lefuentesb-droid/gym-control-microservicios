package com.gym.socio.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MembresiaDTO {

    
    private Integer idMembresia;

    @NotNull(message = "El id del socio es obligatorio")
    private Integer id;
    
    private String nombreSocio;
    
    @NotBlank(message = "El tipo es obligatoria : ")
    private String tipo;
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(ACTIVA|VENCIDA|CANCELADA)$",message = "El estado solo puede ser: ACTIVA, VENCIDA o CANCELADA")
    private String estado;

    private String observaciones; 

}
