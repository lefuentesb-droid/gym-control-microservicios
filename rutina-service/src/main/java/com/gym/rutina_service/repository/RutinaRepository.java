package com.gym.rutina_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gym.rutina_service.model.Rutina;

@Repository
public interface RutinaRepository extends JpaRepository<Rutina, Integer>{


    List<Rutina> findByEstado(Boolean estado);

    List<Rutina> findByNombre(String nombre);

    List<Rutina> findByIdSocio(Integer idSocio);

    List<Rutina> findByIdEntrenador(Integer idEntrenador);
}
