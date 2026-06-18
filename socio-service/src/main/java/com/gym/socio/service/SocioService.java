package com.gym.socio.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gym.socio.dto.SocioDTO;
import com.gym.socio.model.Socio;
import com.gym.socio.repository.SocioRepository;



@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;

    public List<SocioDTO> obtenerTodos() {
        return socioRepository.findAll().stream().map(this::convertirDto).toList();
    }

    public SocioDTO buscarPorId(Integer id){
        Socio soci =socioRepository.findById(id).orElseThrow(() -> new RuntimeException("¡Usuario no encontrado!"));
        return convertirDto(soci);
    }

    public String eliminar(Integer id){
    try {
            Socio socio = socioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! El Usuario con ID " + id + " no existe."));
           socioRepository.delete(socio);
            return "El Usuario '" + socio.getNombre() + "' ha sido retirado del registro exitosamente.";
    } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    
    public Socio guardarSocio(Socio socio) {
        socio.setFechaRegistro(LocalDate.now());
       return socioRepository.save(socio);
    }

    public Socio actualizarSocio(Integer id,Socio socio){
        Socio soci = socioRepository.findById(id).orElseThrow(() -> new RuntimeException("¡El socio no existe en los registros!"));
        if(socio.getRut() != null){
            soci.setRut(socio.getRut());
        }
        if(socio.getNombre() != null){
            soci.setNombre(socio.getNombre());
        }
        if(socio.getApellido() != null){
            soci.setApellido(socio.getApellido());
        }
        if(socio.getCorreo()!=null){
            soci.setCorreo(socio.getCorreo());
        }
        if(socio.getDireccion()!=null){
            soci.setDireccion(socio.getDireccion());
        }
        if(socio.getTelefono()!=null){
            soci.setTelefono(socio.getTelefono());
        }
        if(socio.getFechaNacimiento()!=null){
            soci.setFechaNacimiento(socio.getFechaNacimiento());
        }
        if (socio.getEstado() != null) {
            soci.setEstado(socio.getEstado());
        }
        return socioRepository.save(soci);
    }


    public List<SocioDTO> buscarPorEstado(Boolean estado){
       return socioRepository.findByEstado(estado).stream().map(this::convertirDto).toList();
    }

    private SocioDTO convertirDto(Socio soci) {
        SocioDTO dto = new SocioDTO();
        dto.setIdsocio(soci.getId());
        dto.setRut(soci.getRut());
        dto.setNombre(soci.getNombre());
        dto.setApellido(soci.getApellido());
        dto.setCorreo(soci.getCorreo());
        dto.setDireccion(soci.getDireccion());
        dto.setTelefono(soci.getTelefono());
        dto.setFechaNacimiento(soci.getFechaNacimiento());
        dto.setEstado(soci.getEstado());
        return dto; 
        
    }
}
