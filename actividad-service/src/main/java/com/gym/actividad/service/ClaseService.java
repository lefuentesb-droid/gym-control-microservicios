package com.gym.actividad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.actividad.dto.ClaseDTO;
import com.gym.actividad.model.Clase;
import com.gym.actividad.model.Entrenador;
import com.gym.actividad.repository.ClaseRepository;
import com.gym.actividad.repository.EntrenadorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    public List<ClaseDTO> obtenerTodas() {
        return claseRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public ClaseDTO buscarPorId(Integer id) {

        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

        return convertirDTO(clase);
    }

    public ClaseDTO guardar(ClaseDTO dto) {

        Entrenador entrenador = entrenadorRepository.findById(dto.getIdEntrenador())
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        Clase clase = new Clase();

        clase.setNombre(dto.getNombre());
        clase.setDescripcion(dto.getDescripcion());
        clase.setCupoMaximo(dto.getCupoMaximo());
        clase.setDuracionMinutos(dto.getDuracionMinutos());
        clase.setHorario(dto.getHorario());
        clase.setDiasSemana(dto.getDiasSemana());
        clase.setEstado(dto.getEstado());

        clase.setEntrenador(entrenador);

        Clase guardada = claseRepository.save(clase);

        return convertirDTO(guardada);
    }

    public ClaseDTO actualizar(Integer id, ClaseDTO dto) {

        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

        if (dto.getNombre() != null) {
            clase.setNombre(dto.getNombre());
        }

        if (dto.getDescripcion() != null) {
            clase.setDescripcion(dto.getDescripcion());
        }
        if (dto.getCupoMaximo() != null) {
            clase.setCupoMaximo(dto.getCupoMaximo());
        }
        if (dto.getDuracionMinutos() != null) {
            clase.setDuracionMinutos(dto.getDuracionMinutos());
        }
        if (dto.getHorario() != null) {
            clase.setHorario(dto.getHorario());
        }
        if (dto.getDiasSemana() != null) {
            clase.setDiasSemana(dto.getDiasSemana());
        }
        if (dto.getEstado() != null) {
            clase.setEstado(dto.getEstado());
        }
        if (dto.getIdEntrenador() != null) {

            Entrenador entrenador = entrenadorRepository.findById(dto.getIdEntrenador())
                    .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

            clase.setEntrenador(entrenador);
        }
        Clase actualizada = claseRepository.save(clase);

        return convertirDTO(actualizada);
    }

    public String eliminar(Integer id) {

        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

        clase.setEstado(false);

        claseRepository.save(clase);

        return "Clase deshabilitada correctamente";
    }

    public List<ClaseDTO> buscarPorEstado(Boolean estado) {

        return claseRepository.findByEstado(estado)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public List<ClaseDTO> buscarPorNombre(String nombre) {

        return claseRepository.findByNombre(nombre)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public List<ClaseDTO> buscarPorEntrenador(Integer idEntrenador) {

        return claseRepository.findByEntrenadorId(idEntrenador)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    private ClaseDTO convertirDTO(Clase clase) {

        ClaseDTO dto = new ClaseDTO();

        dto.setId(clase.getId());

        dto.setNombre(clase.getNombre());
        dto.setDescripcion(clase.getDescripcion());

        dto.setCupoMaximo(clase.getCupoMaximo());
        dto.setDuracionMinutos(clase.getDuracionMinutos());

        dto.setHorario(clase.getHorario());
        dto.setDiasSemana(clase.getDiasSemana());

        dto.setEstado(clase.getEstado());

        if (clase.getEntrenador() != null) {

            dto.setIdEntrenador(clase.getEntrenador().getId());
            dto.setNombreEntrenador(clase.getEntrenador().getNombre());
        }

        return dto;
    }
}
