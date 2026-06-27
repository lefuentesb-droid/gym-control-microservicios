package com.gym.pago.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.pago.model.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {

    List<MetodoPago> findByActivo(Boolean activo);

    List<MetodoPago> findByNombre(String nombre);
    
}