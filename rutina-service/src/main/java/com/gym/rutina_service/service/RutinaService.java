package com.gym.rutina_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.rutina_service.dto.RutinaDTO;
import com.gym.rutina_service.model.Rutina;
import com.gym.rutina_service.repository.RutinaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RutinaService {

    @Autowired
    private RutinaRepository rutinaRepository;

    public List<RutinaDTO> obtenerTodas() {
        return rutinaRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public RutinaDTO buscarPorId(Integer id) {
        Rutina rutina = rutinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));

        return convertirDTO(rutina);
    }

    public RutinaDTO guardar(RutinaDTO dto) {
        // El socio y el entrenador viven en otros microservicios (socio-service y
        // actividad-service), por lo que aquí solo guardamos su id. La validación
        // de que existan se hace contra esos servicios vía REST (WebClient/Feign).
        Rutina rutina = new Rutina();

        rutina.setIdSocio(dto.getIdSocio());
        rutina.setIdEntrenador(dto.getIdEntrenador());
        rutina.setNombre(dto.getNombre());
        rutina.setDescripcion(dto.getDescripcion());
        rutina.setFechaAsignacion(dto.getFechaAsignacion());
        rutina.setEstado(dto.getEstado());

        Rutina guardada = rutinaRepository.save(rutina);

        return convertirDTO(guardada);
    }

    public RutinaDTO actualizar(Integer id, RutinaDTO dto) {
        Rutina rutina = rutinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));

        if (dto.getNombre() != null) {
            rutina.setNombre(dto.getNombre());
        }

        if (dto.getDescripcion() != null) {
            rutina.setDescripcion(dto.getDescripcion());
        }

        if (dto.getFechaAsignacion() != null) {
            rutina.setFechaAsignacion(dto.getFechaAsignacion());
        }

        if (dto.getEstado() != null) {
            rutina.setEstado(dto.getEstado());
        }

        if (dto.getIdSocio() != null) {
            rutina.setIdSocio(dto.getIdSocio());
        }

        if (dto.getIdEntrenador() != null) {
            rutina.setIdEntrenador(dto.getIdEntrenador());
        }

        Rutina actualizada = rutinaRepository.save(rutina);

        return convertirDTO(actualizada);
    }

    public String eliminar(Integer id) {
        Rutina rutina = rutinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));

        rutina.setEstado(false);
        rutinaRepository.save(rutina);

        return "Rutina deshabilitada correctamente";
    }

    public List<RutinaDTO> buscarPorEstado(Boolean estado) {
        return rutinaRepository.findByEstado(estado)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public List<RutinaDTO> buscarPorNombre(String nombre) {
        return rutinaRepository.findByNombre(nombre)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public List<RutinaDTO> buscarPorSocio(Integer idSocio) {
        return rutinaRepository.findByIdSocio(idSocio)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public List<RutinaDTO> buscarPorEntrenador(Integer idEntrenador) {
        return rutinaRepository.findByIdEntrenador(idEntrenador)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    private RutinaDTO convertirDTO(Rutina rutina) {
        RutinaDTO dto = new RutinaDTO();

        dto.setId(rutina.getId());
        dto.setNombre(rutina.getNombre());
        dto.setDescripcion(rutina.getDescripcion());
        dto.setFechaAsignacion(rutina.getFechaAsignacion());
        dto.setEstado(rutina.getEstado());
        dto.setIdSocio(rutina.getIdSocio());
        dto.setIdEntrenador(rutina.getIdEntrenador());
        // nombreSocio / nombreEntrenador se agrega mas adelante con la llamada REST a socio-service y actividad-service.
        return dto;
    }
}
