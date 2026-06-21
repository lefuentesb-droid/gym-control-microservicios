package com.gym.rutina_service.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.gym.rutina_service.dto.EjercicioDTO;
import com.gym.rutina_service.model.Ejercicio;
import com.gym.rutina_service.repository.EjercicioRepository;

@Service
@Transactional
public class EjercicioService {

    @Autowired
    private EjercicioRepository repository;

    public List<EjercicioDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::convertirADTO).toList();
    }

    public EjercicioDTO buscarPorId(Integer id) {
        Ejercicio ex = repository.findById(id).orElseThrow(() -> new RuntimeException("No existe"));
        return convertirADTO(ex);
    }

    public EjercicioDTO crear(Ejercicio ejercicio) {
        ejercicio.setEstado(true);
        return convertirADTO(repository.save(ejercicio));
    }

    public Ejercicio actualizar(Integer id, Ejercicio nuevos) {
        Ejercicio ex = repository.findById(id).orElseThrow(() -> new RuntimeException("No existe"));
        
        if (nuevos.getNombre() != null) ex.setNombre(nuevos.getNombre());
        if (nuevos.getDescripcion() != null) ex.setDescripcion(nuevos.getDescripcion());
        if (nuevos.getGrupoMuscular() != null) ex.setGrupoMuscular(nuevos.getGrupoMuscular());
        if (nuevos.getDificultad() != null) ex.setDificultad(nuevos.getDificultad());
        if (nuevos.getVideoUrl() != null) ex.setVideoUrl(nuevos.getVideoUrl());
        if (nuevos.getEstado() != null) ex.setEstado(nuevos.getEstado());
        
        return repository.save(ex);
    }

    public String eliminar(Integer id) {
        try {
            Ejercicio ex = repository.findById(id).orElseThrow(() -> new RuntimeException("No existe"));
            repository.delete(ex); 
            return "Eliminado exitosamente.";
        } catch (Exception e) {
            return "Error al eliminar.";
        }
    }

    private EjercicioDTO convertirADTO(Ejercicio e) {
        EjercicioDTO dto = new EjercicioDTO();
        dto.setId(e.getId());
        dto.setNombre(e.getNombre());
        dto.setDescripcion(e.getDescripcion());
        dto.setGrupoMuscular(e.getGrupoMuscular());
        dto.setDificultad(e.getDificultad());
        dto.setVideoUrl(e.getVideoUrl());
        dto.setEstado(e.getEstado());
        return dto;
    }
}