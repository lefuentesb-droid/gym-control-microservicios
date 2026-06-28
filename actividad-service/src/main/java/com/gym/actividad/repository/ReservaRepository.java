package com.gym.actividad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.gym.actividad.model.Reserva;


public interface ReservaRepository extends JpaRepository<Reserva, Integer> { //basicamente nos da las funciones del CRUD
    List<Reserva> findBySocioId(Integer socioId); //esto busca a las reservas por el id del socio
}
