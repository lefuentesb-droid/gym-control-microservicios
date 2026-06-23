package com.gym.pago_service.dto;

import lombok.Data;

@Data
public class MetodoPagoDTO {

    private Integer idMetodoPago;

    private String nombre;

    private String descripcion;

    private Boolean estado;
}
