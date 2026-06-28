package com.gym.rutina_service.dto;

import lombok.Data;

@Data
public class EjercicioDTO {
    
    private Integer id;
    private String nombre;
    private String descripcion;
    private String grupoMuscular;
    private String dificultad;
    private String videoUrl;
    private Boolean estado;
}