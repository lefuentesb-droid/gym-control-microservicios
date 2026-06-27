package com.gym.pago.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.gym.pago.dto.PagoDTO;
import com.gym.pago.dto.SocioDTO;
import com.gym.pago.model.Pago;
import com.gym.pago.model.MetodoPago;
import com.gym.pago.repository.PagoRepository;
import com.gym.pago.repository.MetodoPagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<PagoDTO> obtenerTodos() {
        return pagoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public PagoDTO buscarPorId(Integer id) {
        if (id == null) {
            throw new RuntimeException("El ID del pago es obligatorio");
        }
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        return convertirADTO(pago);
    }

    public Pago guardar(Pago nuevoPago) {

        validarSocio(nuevoPago.getSocioId());
        validarMetodoPago(nuevoPago.getMetodoPagoId());

        if (nuevoPago.getFechaPago() == null) {
            nuevoPago.setFechaPago(LocalDate.now());
        }

        return pagoRepository.save(nuevoPago);
    }

    public Pago actualizar(Integer id, Pago pago) {

    if (id == null) {
        throw new RuntimeException("El ID del pago es obligatorio");
    }
    if (pago == null) {
        throw new RuntimeException("Los datos del pago son obligatorios");
    }

    Pago existente = pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

    if (pago.getSocioId() != null) {
        validarSocio(pago.getSocioId());
        existente.setSocioId(pago.getSocioId());
    }
    if (pago.getMetodoPagoId() != null) {
        validarMetodoPago(pago.getMetodoPagoId());
        existente.setMetodoPagoId(pago.getMetodoPagoId());
    }
    if (pago.getMonto() != null) {
        existente.setMonto(pago.getMonto());
    }
    if (pago.getFechaPago() != null) {
        existente.setFechaPago(pago.getFechaPago());
    }
    if (pago.getEstado() != null) {
        existente.setEstado(pago.getEstado());
    }
        return pagoRepository.save(existente);
    }

    public String eliminar(Integer id) {

        if (id == null) {
        return "El ID del pago es obligatorio";
        }
        try {
            
            Pago pago = pagoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
            if (pago == null) {
                return "Pago no encontrado";
            }
            pagoRepository.delete(pago);
            return "Pago eliminado correctamente";   
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public List<PagoDTO> buscarPorSocioId(Integer socioId) {
        return pagoRepository.findBySocioId(socioId)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<PagoDTO> buscarPorEstado(String estado) {
        return pagoRepository.findByEstado(estado)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    private void validarSocio(Integer socioId) {
        try {
            SocioDTO socio = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/api/v1/socios/" + socioId)
                    .retrieve()
                    .bodyToMono(SocioDTO.class)
                    .block();

            if (socio == null) {
                throw new RuntimeException("No se pudo validar el socio asociado al pago");
            }

        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("No existe un socio con ID: " + socioId);
        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con socio-service: " + e.getMessage());
        }
    }

    private void validarMetodoPago(Integer metodoPagoId) {
        MetodoPago metodoPago = metodoPagoRepository.findById(metodoPagoId)
                .orElseThrow(() -> new RuntimeException("No existe un método de pago con ID: " + metodoPagoId));

        if (!metodoPago.getActivo()) {
            throw new RuntimeException("El método de pago no está activo");
            }
        }
        
    private PagoDTO convertirADTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setSocioId(pago.getSocioId());
        dto.setMetodoPagoId(pago.getMetodoPagoId());
        dto.setMonto(pago.getMonto());
        dto.setFechaPago(pago.getFechaPago());
        dto.setEstado(pago.getEstado());
        return dto;
    }
}