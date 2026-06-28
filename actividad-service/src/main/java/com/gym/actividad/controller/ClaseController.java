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
import com.gym.actividad.dto.ClaseDTO;
import com.gym.actividad.service.ClaseService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clases")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    public ResponseEntity<List<ClaseDTO>> listarTodas() {
        List<ClaseDTO> clases = claseService.obtenerTodas();
        return clases.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(clases, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaseDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(claseService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ClaseDTO> crearClase(@Valid @RequestBody ClaseDTO dto) {
        try {
            ClaseDTO nueva = claseService.guardar(dto);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaseDTO> actualizarClase(@PathVariable Integer id, @RequestBody ClaseDTO dto) {
        try {
            return new ResponseEntity<>(claseService.actualizar(id, dto), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarClase(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(claseService.eliminar(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ClaseDTO>> buscarPorEstado(@PathVariable Boolean estado) {
        List<ClaseDTO> clases = claseService.buscarPorEstado(estado);
        return clases.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(clases, HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ClaseDTO>> buscarPorNombre(@PathVariable String nombre) {
        List<ClaseDTO> clases = claseService.buscarPorNombre(nombre);
        return clases.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(clases, HttpStatus.OK);
    }

    @GetMapping("/entrenador/{idEntrenador}")
    public ResponseEntity<List<ClaseDTO>> buscarPorEntrenador(@PathVariable Integer idEntrenador) {
        List<ClaseDTO> clases = claseService.buscarPorEntrenador(idEntrenador);
        return clases.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(clases, HttpStatus.OK);
    }
}
