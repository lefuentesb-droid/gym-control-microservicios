package com.gym.pago.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.pago.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

    List<Pago> findBySocioId(Integer socioId); //esto busca a los pagos por el id del socio
    List<Pago> findByEstado(String estado); //esto busca a los pagos por su estado (true o false)

}