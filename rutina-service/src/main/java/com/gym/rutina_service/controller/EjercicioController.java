package com.gym.rutina_service.controller;

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

import com.gym.rutina_service.dto.EjercicioDTO;
import com.gym.rutina_service.model.Ejercicio;
import com.gym.rutina_service.service.EjercicioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ejercicios")
public class EjercicioController {

    @Autowired
    private EjercicioService ejercicioService;

    @GetMapping
    public ResponseEntity<List<EjercicioDTO>> listarTodos() {
        List<EjercicioDTO> ejercicios = ejercicioService.obtenerTodos();

        if (ejercicios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(ejercicios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EjercicioDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(ejercicioService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<EjercicioDTO> crearEjercicio(@Valid @RequestBody Ejercicio ejercicio) {
        return new ResponseEntity<>(ejercicioService.crear(ejercicio), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ejercicio> actualizarEjercicio(@PathVariable Integer id,@RequestBody Ejercicio ejercicio) {
        try {
            return new ResponseEntity<>(ejercicioService.actualizar(id, ejercicio), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEjercicio(@PathVariable Integer id) {
        String resultado = ejercicioService.eliminar(id);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
