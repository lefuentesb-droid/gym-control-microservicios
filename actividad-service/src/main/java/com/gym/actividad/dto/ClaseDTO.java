package com.gym.actividad.dto;

import lombok.Data;

@Data
public class ClaseDTO {

    private Integer id;
    
    private Integer idEntrenador;
    private String nombreEntrenador;

    private String nombre;
    private String descripcion;

    private Integer cupoMaximo;
    private Integer duracionMinutos;

    private String horario;
    private String diasSemana;

    private Boolean estado;
}
