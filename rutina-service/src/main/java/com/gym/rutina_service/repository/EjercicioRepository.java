package com.gym.rutina_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gym.rutina_service.model.Ejercicio;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Integer> {
    List<Ejercicio> findByEstado(Boolean estado);
}