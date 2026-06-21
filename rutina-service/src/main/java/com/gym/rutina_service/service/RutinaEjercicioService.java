package com.gym.rutina_service.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.gym.rutina_service.dto.RutinaEjercicioDTO;
import com.gym.rutina_service.model.RutinaEjercicio;
import com.gym.rutina_service.repository.RutinaEjercicioRepository;

@Service
@Transactional
public class RutinaEjercicioService {

    @Autowired
    private RutinaEjercicioRepository repository;

    public List<RutinaEjercicioDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::convertirADTO).toList();
    }

    public RutinaEjercicioDTO crear(RutinaEjercicio rutina) {
        return convertirADTO(repository.save(rutina));
    }

    public RutinaEjercicio actualizar(Integer id, RutinaEjercicio nuevos) {
        RutinaEjercicio ex = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe"));
        
        if (nuevos.getSeries() != null) ex.setSeries(nuevos.getSeries());
        if (nuevos.getRepeticiones() != null) ex.setRepeticiones(nuevos.getRepeticiones());
        if (nuevos.getDescansoSegundos() != null) ex.setDescansoSegundos(nuevos.getDescansoSegundos());
        if (nuevos.getOrden() != null) ex.setOrden(nuevos.getOrden());
        if (nuevos.getObservaciones() != null) ex.setObservaciones(nuevos.getObservaciones());
        
        return repository.save(ex);
    }

    public String eliminar(Integer id) {
        try {
            repository.deleteById(id); 
            return "Eliminado exitosamente.";
        } catch (Exception e) {
            return "Error al eliminar.";
        }
    }

    private RutinaEjercicioDTO convertirADTO(RutinaEjercicio rutina) {
        RutinaEjercicioDTO dto = new RutinaEjercicioDTO();
        dto.setId(rutina.getId());
        dto.setSeries(rutina.getSeries());
        dto.setRepeticiones(rutina.getRepeticiones());
        dto.setDescansoSegundos(rutina.getDescansoSegundos());
        dto.setOrden(rutina.getOrden());
        dto.setObservaciones(rutina.getObservaciones());
        
        if (rutina.getRutina() != null) {
            dto.setRutinaCabeceraId(rutina.getRutina().getId());
        }

        if (rutina.getEjercicio() != null) {
            dto.setEjercicioId(rutina.getEjercicio().getId());
        }
        
        return dto;
    }
}