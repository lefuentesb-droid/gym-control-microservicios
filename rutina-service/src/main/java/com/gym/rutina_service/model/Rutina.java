package com.gym.rutina_service.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "rutinas")
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El id del socio es obligatorio")
    @Column(name = "id_socio", nullable = false)
    private Integer idSocio;

    @NotNull(message = "El id del entrenador es obligatorio")
    @Column(name = "id_entrenador", nullable = false)
    private Integer idEntrenador;

    @NotBlank(message = "El nombre de la rutina es obligatorio")
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @NotNull(message = "La fecha de asignación es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaAsignacion;

    @NotNull(message = "El estado es obligatorio")
    @Column(nullable = false)
    private Boolean estado;
}
