package com.gym.actividad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.actividad.dto.ReservaDTO;
import com.gym.actividad.model.Clase;
import com.gym.actividad.model.Reserva;
import com.gym.actividad.repository.ClaseRepository;
import com.gym.actividad.repository.ReservaRepository;
import org.springframework.web.reactive.function.client.WebClient;
import com.gym.actividad.dto.SocioDTO;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReservaService {

    @Autowired //esto llama al reserva repository para que pueda usar sus metodos
    private ReservaRepository reservaRepository;

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;
    
    public List<ReservaDTO> obtenerTodas() { //busca la lista de reservas y los convierte a DTO para que el usuario entienda mejor la informacion
        return reservaRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    //Buscar por ID
    public ReservaDTO buscarPorId(Integer id) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("¡Reserva no encontrada!"));
        return convertirADTO(reserva);
    }

    //Crear reserva
    public ReservaDTO crearReserva(ReservaDTO dto) {

        Clase clase = claseRepository.findById(dto.getClaseId())
                .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

        SocioDTO socio = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/v1/socios/" + dto.getSocioId())
                .retrieve()
                .bodyToMono(SocioDTO.class)
                .block();

        if (socio == null) {
            throw new RuntimeException("Socio no encontrado");
        }

        Reserva reserva = new Reserva();

        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setEstado(dto.getEstado());
        reserva.setSocioId(dto.getSocioId());
        reserva.setClase(clase);

        Reserva guardada = reservaRepository.save(reserva);

        return convertirADTO(guardada);
    }
    

    //Actualizar reserva
    public Reserva actualizarReserva(Integer id, Reserva reserva) {
        Reserva res = reservaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("¡Reserva no encontrada!"));
        
        if (reserva.getFechaReserva() != null) {
            res.setFechaReserva(reserva.getFechaReserva());
        }
        if (reserva.getEstado() != null) {
            res.setEstado(reserva.getEstado());
        }
        return reservaRepository.save(res);
    }

    //Eliminar reserva
    public String eliminar(Integer id) {
        try {
            Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! La Reserva con ID " + id + " no existe."));
            reservaRepository.delete(reserva);
            return "La reserva " + reserva.getId() + " ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    //Metodo para convertir una reserva a un DTO
    private ReservaDTO convertirADTO(Reserva reserva) { //convierte una reserva a un DTO para que el usuario le sea mas facil entender la informacion
        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setFechaReserva(reserva.getFechaReserva());
        dto.setEstado(reserva.getEstado());
        dto.setSocioId(reserva.getClase().getId());

        if(reserva.getClase()!=null){
            dto.setClaseId(reserva.getClase().getId());
        }
        return dto;
    }
}
