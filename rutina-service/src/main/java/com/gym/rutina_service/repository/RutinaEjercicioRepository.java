package com.gym.rutina_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gym.rutina_service.model.RutinaEjercicio;

@Repository
public interface RutinaEjercicioRepository extends JpaRepository<RutinaEjercicio, Integer> { 
    
}