package com.gym.socio.dto;
import java.time.LocalDate;
import lombok.Data;

@Data
public class SocioDTO {

    private Integer idsocio;
    
    private String rut;

    
    private String nombre;

   
    private String apellido;

   
    private String correo;

    private String telefono;

    
    private String direccion;

    private LocalDate fechaNacimiento;

    
    private Boolean estado;
    
}
