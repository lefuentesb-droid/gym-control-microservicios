package com.gym.actividad.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.gym.actividad.dto.EntrenadorDTO;
import com.gym.actividad.model.Entrenador;
import com.gym.actividad.service.EntrenadorService;

@RestController
@RequestMapping("/api/v1/entrenadores")
public class EntrenadorController {

    @Autowired
    private EntrenadorService entrenadorService;

    // GET: Obtener todos los entrenadores
    @GetMapping
    public ResponseEntity<List<EntrenadorDTO>> listarEntrenadores() {
        List<EntrenadorDTO> entrenadores = entrenadorService.obtenerTodos();
        if (entrenadores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        }
        return new ResponseEntity<>(entrenadores, HttpStatus.OK); 
    }

    //GET: Buscar un entrenador por su ID
    @GetMapping("/{id}")
    public ResponseEntity<EntrenadorDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(entrenadorService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }

    //POST: Crear un nuevo entrenador
    @PostMapping
    public ResponseEntity<EntrenadorDTO> crearEntrenador(@Valid @RequestBody Entrenador entrenador) {
        try {
            return new ResponseEntity<>(entrenadorService.crearEntrenador(entrenador), HttpStatus.CREATED); // 201
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //PUT: Actualizar un entrenador existente
    @PutMapping("/{id}")
    public ResponseEntity<EntrenadorDTO> actualizarEntrenador(@PathVariable Integer id, @RequestBody Entrenador entrenador) {
        try {
            Entrenador actualizado = entrenadorService.actualizarEntrenador(id, entrenador);
            return new ResponseEntity<>(entrenadorService.buscarPorId(actualizado.getId()), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE: Eliminar un entrenador (Borrado)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEntrenador(@PathVariable Integer id) {
        String resultado = entrenadorService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}