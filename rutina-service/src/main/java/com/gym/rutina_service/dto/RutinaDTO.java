package com.gym.rutina_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RutinaDTO {


    private Integer id;

    @NotNull(message = "El id del socio es obligatorio")
    private Integer idSocio;

    private String nombreSocio;

    @NotNull(message = "El id del entrenador es obligatorio")
    private Integer idEntrenador;

    private String nombreEntrenador;

    @NotBlank(message = "El nombre de la rutina es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;
    
    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String descripcion;

    @NotNull(message = "La fecha de asignación es obligatoria")
    private LocalDate fechaAsignacion;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}
