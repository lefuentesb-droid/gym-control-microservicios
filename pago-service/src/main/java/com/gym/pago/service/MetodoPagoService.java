package com.gym.pago.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.pago.dto.MetodoPagoDTO;
import com.gym.pago.model.MetodoPago;
import com.gym.pago.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public List<MetodoPagoDTO> obtenerTodos() {
        return metodoPagoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public MetodoPagoDTO buscarPorId(Integer id) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        return convertirADTO(metodoPago);
    }

    public MetodoPago guardar(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
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

        if (metodoPago.getActivo() != null) {
            existente.setActivo(metodoPago.getActivo());
        }

        return metodoPagoRepository.save(existente);
    }

    public String eliminar(Integer id) {
        try {
            MetodoPago metodoPago = metodoPagoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

            metodoPagoRepository.delete(metodoPago);
            return "Método de pago eliminado correctamente";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public List<MetodoPagoDTO> buscarPorActivo(Boolean activo) {
        return metodoPagoRepository.findByActivo(activo)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    private MetodoPagoDTO convertirADTO(MetodoPago metodoPago) {
        MetodoPagoDTO dto = new MetodoPagoDTO();
        dto.setId(metodoPago.getId());
        dto.setNombre(metodoPago.getNombre());
        dto.setDescripcion(metodoPago.getDescripcion());
        dto.setActivo(metodoPago.getActivo());
        return dto;
    }
}