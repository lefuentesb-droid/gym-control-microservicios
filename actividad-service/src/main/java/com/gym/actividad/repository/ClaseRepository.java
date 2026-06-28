package com.gym.actividad.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gym.actividad.model.Clase;

public interface ClaseRepository extends JpaRepository<Clase, Integer> {

    List<Clase> findByEstado(Boolean estado);

    List<Clase> findByNombre(String nombre);

    List<Clase> findByEntrenadorId(Integer idEntrenador);
    
}
