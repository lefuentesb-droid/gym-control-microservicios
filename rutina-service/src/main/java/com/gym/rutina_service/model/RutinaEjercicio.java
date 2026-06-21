package com.gym.rutina_service.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "rutina_ejercicio")
public class RutinaEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Las series son obligatorias")
    @Column(nullable = false)
    private Integer series;

    @NotNull(message = "Las repeticiones son obligatorias")
    @Column(nullable = false)
    private Integer repeticiones;

    @NotNull(message = "El tiempo de descanso es obligatorio")
    @Column(name = "descanso_segundos", nullable = false)
    private Integer descansoSegundos;

    @NotNull(message = "El orden del ejercicio es obligatorio")
    @Column(nullable = false)
    private Integer orden;

    @Size(max = 255, message = "Las observaciones no pueden superar los 255 caracteres")
    @Column(length = 255)
    private String observaciones;

    //Relación con rutina (FK)
    @ManyToOne
    @JoinColumn(name = "id_rutina")
    private Rutina rutina;

    //Relacion con ejercicio (FK)
    @ManyToOne
    @JoinColumn(name = "id_ejercicio")
    private Ejercicio ejercicio;
    
}