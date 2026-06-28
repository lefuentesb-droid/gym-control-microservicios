package com.gym.actividad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.actividad.model.Entrenador;


public interface EntrenadorRepository extends JpaRepository<Entrenador, Integer> {
    List<Entrenador> findByEstado(Boolean estado);
}