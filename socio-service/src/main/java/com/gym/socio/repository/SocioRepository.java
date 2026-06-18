package com.gym.socio.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.socio.model.Socio;

public interface SocioRepository extends JpaRepository<Socio,Integer>{

    List<Socio> findByEstado(Boolean estado);//Buscar los socios por estado
}
