package com.gym.actividad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.actividad.dto.ReservaDTO;
import com.gym.actividad.model.Reserva;
import com.gym.actividad.service.ReservaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping //Listar/Obtener todas las reservas
    public ResponseEntity<List<ReservaDTO>> todasLasReservas() { 
        List<ReservaDTO> reservas = reservaService.obtenerTodas();
        if (reservas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @PostMapping 
    public ResponseEntity<ReservaDTO> crearReserva(@Valid @RequestBody ReservaDTO dto) {
        try {
            ReservaDTO nueva = reservaService.crearReserva(dto);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}") //Recibe una reserva actualizada y el id de la reserva a actualizar, si falla devuelve un bad request
    public ResponseEntity<ReservaDTO> actualizarReserva(@PathVariable Integer id, @RequestBody Reserva reserva) {
        try {
            Reserva newRes = reservaService.actualizarReserva(id, reserva);
            ReservaDTO dto = reservaService.buscarPorId(newRes.getId());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") //Recibe el id de la reserva a eliminar, si falla devuelve un not found
    public ResponseEntity<String> eliminarReserva(@PathVariable Integer id) {
        String resultado = reservaService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
    
}
