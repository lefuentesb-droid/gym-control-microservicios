package com.gym.socio.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Entity//@Entity: define la clase como una entidad en la base de datos
@Table(name = "socios")//@Table(name = "socios") : definimos que la tabla en la base de datos,se llama socios
public class Socio {

    @Id//@Id: el atributo es una llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//@GeneratedValue(strategy = GenerationType.IDENTITY): el identificador será autoincrementable e irá de uno en uno
    private Integer id;

    @NotBlank (message = "El rut es obligatorio")
    @Column(nullable = false, length = 12)
    private String rut;

    @NotBlank (message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank (message = "El apellido es obligatorio")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50  caracteres")
    @Column(nullable = false, length = 50)
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Size(min = 3, max = 100, message = "El correo debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String correo;

    @NotBlank (message = "El telefono es obligatorio")
    @Column(nullable = false, length = 20)
    private String telefono;
    
    @NotBlank (message = "La  direccion es obligatoria")
    @Size(min = 3, max = 100, message = "la direccion debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 150)
    private String direccion;

   
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotNull(message = "El estado es obligatorio")
    @Column(nullable = false)
    private Boolean estado;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;


}

