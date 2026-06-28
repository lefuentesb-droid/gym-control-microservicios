package com.gym.pago_service.model;

import java.time.LocalDate;

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
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El monto es obligatorio")
    @Min(value = 1, message = "El monto debe ser mayor a 0")
    @Column(nullable = false)
    private Double monto;

    @NotNull(message = "La fecha de pago es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaPago;

    @NotBlank(message = "El número de comprobante es obligatorio")
    @Size(min = 5, max = 50, message = "El comprobante debe tener entre 5 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String comprobante;

    @NotNull(message = "El estado es obligatorio")
    @Column(nullable = false)
    private Boolean estado;

    // El socio y la membresía viven en socio-service (otra base de datos),
    // por lo que aquí solo guardamos su id. Se validan vía REST (WebClient).
    @NotNull(message = "El id del socio es obligatorio")
    @Min(value = 1, message = "El id del socio debe ser mayor a 0")
    @Column(name = "id_socio", nullable = false)
    private Integer idSocio;

    @NotNull(message = "El id de la membresía es obligatorio")
    @Min(value = 1, message = "El id de la membresía debe ser mayor a 0")
    @Column(name = "id_membresia", nullable = false)
    private Integer idMembresia;

    // MetodoPago sí pertenece a este microservicio -> relación JPA real.
    @NotNull(message = "El método de pago es obligatorio")
    @ManyToOne
    @JoinColumn(name = "id_metodo_pago", nullable = false)
    private MetodoPago metodoPago;
}
