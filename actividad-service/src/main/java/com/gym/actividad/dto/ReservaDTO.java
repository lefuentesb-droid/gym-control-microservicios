package com.gym.actividad.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReservaDTO {

    private Integer id;
    private LocalDate fechaReserva;
    private String estado;
    private Integer claseId;
    private Integer socioId;
  
}
