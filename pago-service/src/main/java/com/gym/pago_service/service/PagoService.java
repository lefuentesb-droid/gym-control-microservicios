package com.gym.pago_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.pago_service.dto.PagoDTO;
import com.gym.pago_service.model.MetodoPago;
import com.gym.pago_service.model.Pago;
import com.gym.pago_service.repository.MetodoPagoRepository;
import com.gym.pago_service.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public List<PagoDTO> obtenerTodos() {
        return pagoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public PagoDTO buscarPorId(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        return convertirADTO(pago);
    }

    public PagoDTO registrarPago(PagoDTO dto) {
        MetodoPago metodoPago = metodoPagoRepository.findById(dto.getIdMetodoPago())
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        if (!metodoPago.getEstado()) {
            throw new RuntimeException("El método de pago no está disponible");
        }

        Pago pago = new Pago();
        pago.setMonto(dto.getMonto());
        pago.setFechaPago(dto.getFechaPago());
        pago.setComprobante(dto.getComprobante());
        pago.setEstado(true);
        pago.setIdSocio(dto.getIdSocio());
        pago.setIdMembresia(dto.getIdMembresia());
        pago.setMetodoPago(metodoPago);

        Pago guardado = pagoRepository.save(pago);

        return convertirADTO(guardado);
    }

    public PagoDTO actualizarPago(Integer id, PagoDTO dto) {
        Pago existente = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        if (dto.getMonto() != null) {
            existente.setMonto(dto.getMonto());
        }
        if (dto.getFechaPago() != null) {
            existente.setFechaPago(dto.getFechaPago());
        }
        if (dto.getComprobante() != null) {
            existente.setComprobante(dto.getComprobante());
        }
        if (dto.getEstado() != null) {
            existente.setEstado(dto.getEstado());
        }
        if (dto.getIdSocio() != null) {
            existente.setIdSocio(dto.getIdSocio());
        }
        if (dto.getIdMembresia() != null) {
            existente.setIdMembresia(dto.getIdMembresia());
        }
        if (dto.getIdMetodoPago() != null) {
            MetodoPago metodoPago = metodoPagoRepository.findById(dto.getIdMetodoPago())
                    .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
            existente.setMetodoPago(metodoPago);
        }

        Pago actualizado = pagoRepository.save(existente);

        return convertirADTO(actualizado);
    }

    public String eliminar(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        pago.setEstado(false);
        pagoRepository.save(pago);

        return "Pago deshabilitado correctamente";
    }

    public List<PagoDTO> buscarPorSocio(Integer idSocio) {
        return pagoRepository.findByIdSocio(idSocio)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<PagoDTO> buscarPorEstado(Boolean estado) {
        return pagoRepository.findByEstado(estado)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    private PagoDTO convertirADTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setMonto(pago.getMonto());
        dto.setFechaPago(pago.getFechaPago());
        dto.setComprobante(pago.getComprobante());
        dto.setEstado(pago.getEstado());
        dto.setIdSocio(pago.getIdSocio());
        dto.setIdMembresia(pago.getIdMembresia());

        if (pago.getMetodoPago() != null) {
            dto.setIdMetodoPago(pago.getMetodoPago().getIdMetodoPago());
            dto.setNombreMetodoPago(pago.getMetodoPago().getNombre());
        }

        // La consulta a socio-service se agregará cuando el contrato de sus endpoints esté terminado.

        return dto;
    }
}
