package com.gym.actividad.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.gym.actividad.dto.EntrenadorDTO;
import com.gym.actividad.model.Entrenador;
import com.gym.actividad.repository.EntrenadorRepository;

@Service
@Transactional
public class EntrenadorService {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    public List<EntrenadorDTO> obtenerTodos() {
        return entrenadorRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public EntrenadorDTO buscarPorId(Integer id) {
        Entrenador entrenador = entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Entrenador no encontrado!"));
        return convertirADTO(entrenador);
    }

    public EntrenadorDTO crearEntrenador(Entrenador entrenador) {
        entrenador.setEstado(true); 
        entrenador.setFechaRegistro(LocalDate.now()); // asignacion manual del timestamp
        
        return convertirADTO(entrenadorRepository.save(entrenador));
    }

    public Entrenador actualizarEntrenador(Integer id, Entrenador datosNuevos) {
        
        Entrenador existente = entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Entrenador no encontrado!"));

        if (datosNuevos.getNombre() != null) existente.setNombre(datosNuevos.getNombre());
        if (datosNuevos.getApellido() != null) existente.setApellido(datosNuevos.getApellido());
        if (datosNuevos.getEspecialidad() != null) existente.setEspecialidad(datosNuevos.getEspecialidad());
        if (datosNuevos.getCorreo() != null) existente.setCorreo(datosNuevos.getCorreo());
        if (datosNuevos.getTelefono() != null) existente.setTelefono(datosNuevos.getTelefono());
        if (datosNuevos.getEstado() != null) existente.setEstado(datosNuevos.getEstado()); 
        return entrenadorRepository.save(existente);
    }

    public String eliminar(Integer id) {
        try {
            Entrenador entrenador = entrenadorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡ID " + id + " no existe!"));
            entrenadorRepository.delete(entrenador);
            return "El entrenador ha sido eliminado exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private EntrenadorDTO convertirADTO(Entrenador e) {
        EntrenadorDTO dto = new EntrenadorDTO();
        dto.setId(e.getId()); 
        dto.setNombre(e.getNombre());
        dto.setApellido(e.getApellido());
        dto.setEspecialidad(e.getEspecialidad());
        dto.setCorreo(e.getCorreo());
        dto.setTelefono(e.getTelefono());
        dto.setEstado(e.getEstado());
        dto.setFechaRegistro(e.getFechaRegistro());
        return dto;
    }
}