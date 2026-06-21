package com.gym.rutina_service.model;


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
@Table(name = "ejercicios")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del ejercicio es obligatorio")
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, max = 255)
    @Column(nullable = false, length = 255)
    private String descripcion;

    @NotBlank(message = "El grupo muscular es obligatorio")
    @Column(name = "grupo_muscular", nullable = false, length = 50)
    private String grupoMuscular;

    @NotBlank(message = "La dificultad es obligatoria")
    @Column(nullable = false, length = 20)
    private String dificultad; 

    @Size(max = 255, message = "La URL del video no puede superar los 255 caracteres")
    @Column(name = "video_url", length = 255)
    private String videoUrl; 

    @NotNull(message = "El estado es obligatorio")
    @Column(nullable = false)
    private Boolean estado;
}