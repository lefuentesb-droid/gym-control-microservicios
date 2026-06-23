package com.gym.pago_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gym.pago_service.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    List<Pago> findByIdSocio(Integer idSocio);

    List<Pago> findByEstado(Boolean estado);
}
