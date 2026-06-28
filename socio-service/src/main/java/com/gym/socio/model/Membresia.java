package com.gym.socio.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "membresia")
public class Membresia {

    @Id//@Id: el atributo es una llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//@GeneratedValue(strategy = GenerationType.IDENTITY): el identificador será autoincrementable e irá de uno en uno
    private Integer idmembresia;

    @ManyToOne // osea que varias membresías pertenecen a un solo socio
    @JoinColumn(name = "id_socio", nullable = false) // Define la llave foránea en la tabla membresia y asegura que no sea nula
    private Socio socio;

    @NotBlank (message = "El tipo es obligatoria")
    @Size(min = 3, max = 50, message = "El tipo debe tener al menos 3 caracteres")
    @Column(nullable = false, length = 50)
    private String tipo;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin de la membresia  es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaFin;

    @NotBlank(message = "El estado es obligatorio: ACTIVA/VENCIDA/CANCELADA")
    @Pattern(regexp = "^(ACTIVA|VENCIDA|CANCELADA)$",message = "El estado solo puede ser: ACTIVA, VENCIDA o CANCELADA")
    @Column(nullable = false, length = 20)
    private String estado;

    @Column(length = 200)//no siento que sea obligatorio le quite el nullable y el @notblank
    private String observaciones;


}
