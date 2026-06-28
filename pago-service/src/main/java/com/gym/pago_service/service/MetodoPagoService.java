package com.gym.pago_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.pago_service.dto.MetodoPagoDTO;
import com.gym.pago_service.model.MetodoPago;
import com.gym.pago_service.repository.MetodoPagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public List<MetodoPagoDTO> obtenerTodos() {
        return metodoPagoRepository.findAll().stream().map(this::convertirDto).toList();
    }

    public MetodoPagoDTO buscarPorId(Integer id) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        return convertirDto(metodoPago);
    }

    public MetodoPagoDTO guardar(MetodoPago metodoPago) {
        MetodoPago guardado = metodoPagoRepository.save(metodoPago);
        return convertirDto(guardado);
    }

    public MetodoPago actualizar(Integer id, MetodoPago metodoPago) {
        MetodoPago existente = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        if (metodoPago.getNombre() != null) {
            existente.setNombre(metodoPago.getNombre());
        }
        if (metodoPago.getDescripcion() != null) {
            existente.setDescripcion(metodoPago.getDescripcion());
        }
        if (metodoPago.getEstado() != null) {
            existente.setEstado(metodoPago.getEstado());
        }
        return metodoPagoRepository.save(existente);
    }

    public String eliminar(Integer id) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        metodoPago.setEstado(false);
        metodoPagoRepository.save(metodoPago);
        return "Método de pago deshabilitado correctamente";
    }

    public List<MetodoPagoDTO> buscarPorNombre(String nombre) {
        return metodoPagoRepository.findByNombre(nombre).stream().map(this::convertirDto).toList();
    }

    public List<MetodoPagoDTO> buscarPorEstado(Boolean estado) {
        return metodoPagoRepository.findByEstado(estado).stream().map(this::convertirDto).toList();
    }

    private MetodoPagoDTO convertirDto(MetodoPago metodoPago) {
        MetodoPagoDTO dto = new MetodoPagoDTO();

        dto.setIdMetodoPago(metodoPago.getIdMetodoPago());
        dto.setNombre(metodoPago.getNombre());
        dto.setDescripcion(metodoPago.getDescripcion());
        dto.setEstado(metodoPago.getEstado());

        return dto;
    }
}
