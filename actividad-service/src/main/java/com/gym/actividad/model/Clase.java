package com.gym.actividad.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "clases")

public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_entrenador", nullable = false)
    private Entrenador entrenador;

    @NotBlank(message = "El nombre de la clase es obligatorio")
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @NotNull(message = "El cupo máximo es obligatorio")
    @Min(value = 1, message = "El cupo máximo debe ser mayor a 0")
    @Column(nullable = false)
    private Integer cupoMaximo;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 15, message = "La duración mínima debe ser de 15 minutos")
    @Column(nullable = false)
    private Integer duracionMinutos;

    @NotBlank(message = "El horario es obligatorio")
    @Column(nullable = false, length = 20)
    private String horario;

    @NotBlank(message = "Los días de la semana son obligatorios")
    @Column(nullable = false, length = 100)
    private String diasSemana;

    @NotNull(message = "El estado es obligatorio")
    @Column(nullable = false)
    private Boolean estado;

}
