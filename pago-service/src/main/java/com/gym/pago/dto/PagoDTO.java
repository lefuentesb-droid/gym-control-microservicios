package com.gym.pago.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PagoDTO {

    private Integer id;
    private Integer socioId;
    private Integer metodoPagoId;
    private Integer monto;
    private LocalDate fechaPago;
    private String estado;
    
}