package com.gym.rutina_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.gym.rutina_service.dto.RutinaEjercicioDTO;
import com.gym.rutina_service.model.RutinaEjercicio;
import com.gym.rutina_service.service.RutinaEjercicioService;

@RestController
@RequestMapping("/api/v1/rutina-ejercicios")
public class RutinaEjercicioController {

    @Autowired
    private RutinaEjercicioService service;

    @GetMapping
    public ResponseEntity<List<RutinaEjercicioDTO>> listar() {
        List<RutinaEjercicioDTO> lista = service.obtenerTodos();
        if(lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RutinaEjercicioDTO> crear(@Valid @RequestBody RutinaEjercicio rutina) {
        try {
            return new ResponseEntity<>(service.crear(rutina), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RutinaEjercicio> actualizar(@PathVariable Integer id, @RequestBody RutinaEjercicio rutina) {
        try {
            return new ResponseEntity<>(service.actualizar(id, rutina), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        String res = service.eliminar(id);
        if (res.contains("exitosamente")) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }
}