package com.gym.socio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.socio.dto.MembresiaDTO;
import com.gym.socio.model.Membresia;
import com.gym.socio.model.Socio;
import com.gym.socio.repository.MembresiaRepository;
import com.gym.socio.repository.SocioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MembresiaService {
    @Autowired
    private MembresiaRepository membresiaRepository;
    @Autowired
    private SocioRepository socioRepository;


    public List<MembresiaDTO>obtenerTodos(){
        return membresiaRepository.findAll().stream().map(this::convertirDto).toList();
    }

    public MembresiaDTO buscarPorId(Integer id) {
        Membresia membresia = membresiaRepository.findById(id).orElseThrow(() -> new RuntimeException("¡membresia no encontrada!"));
        return convertirDto(membresia);
    }

    public String eliminarMembresia(Integer id) {
    try {
            Membresia membresia = membresiaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! La membresia  con ID " + id + " no existe."));
            membresiaRepository.delete(membresia);
            return "Membresia '" + membresia.getEstado() + "' ha sido retirado del sistema exitosamente.";
    } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public String asignarMembresiaSocio(Integer idSocio, Integer idmembresia) {
        Socio socio= socioRepository.findById(idSocio)
                .orElseThrow(() -> new RuntimeException("No se encontró el Socio"));
        Membresia membresia = membresiaRepository.findById(idmembresia)
                .orElseThrow(() -> new RuntimeException("No se encontró la membresia"));  
        membresia.setSocio(socio);
        membresiaRepository.save(membresia);
        return "membresia asignada al socio correctamente";
    }


    public String eliminarMembresiaSocio(Integer membresiaID, Integer socioId) {
        Socio socio = socioRepository.findById(socioId)
            .orElseThrow(() -> new RuntimeException("El socio no existe."));
        Membresia membresia = membresiaRepository.findById(membresiaID)
            .orElseThrow(() -> new RuntimeException("La membresia no existe."));
        if (!membresia.getSocio().getId().equals(socio.getId())) {
            return "La membresía no pertenece a este socio";
        }
        membresia.setEstado("CANCELADA");
        membresiaRepository.save(membresia);
        return "Membresiaa cancelada correctamente para el socio " + membresia.getSocio().getNombre();
    } 
    
    public List<MembresiaDTO> buscarPorEstado(String estado) {
        return membresiaRepository.findByEstado(estado).stream().map(this::convertirDto).toList();
    }

    public List<MembresiaDTO> buscarPorTipo(String tipo) {
        return membresiaRepository.findByTipo(tipo).stream().map(this::convertirDto).toList();
    }

    public List<MembresiaDTO> buscarPorSocio(Integer idSocio) {
        return membresiaRepository.findBySocioId(idSocio).stream().map(this::convertirDto).toList();
    }

    public MembresiaDTO guardar(MembresiaDTO dto) {
    Socio socio = socioRepository.findById(dto.getId())
            .orElseThrow(() -> new RuntimeException("No se encontró el socio"));

    Membresia membresia = new Membresia();
    membresia.setSocio(socio);
    membresia.setTipo(dto.getTipo());
    membresia.setFechaInicio(dto.getFechaInicio());
    membresia.setFechaFin(dto.getFechaFin());
    membresia.setEstado(dto.getEstado());
    membresia.setObservaciones(dto.getObservaciones());

    Membresia guardada = membresiaRepository.save(membresia);
    return convertirDto(guardada);
}

    private MembresiaDTO convertirDto(Membresia membresia) {
    MembresiaDTO membre = new MembresiaDTO();

    membre.setIdMembresia(membresia.getIdmembresia());
    membre.setTipo(membresia.getTipo());
    membre.setFechaInicio(membresia.getFechaInicio());
    membre.setFechaFin(membresia.getFechaFin());
    membre.setEstado(membresia.getEstado());
    membre.setObservaciones(membresia.getObservaciones());

    if (membresia.getSocio() != null) {
        membre.setId(membresia.getSocio().getId());
        membre.setNombreSocio(membresia.getSocio().getNombre());
    }

    return membre;
    }
}

