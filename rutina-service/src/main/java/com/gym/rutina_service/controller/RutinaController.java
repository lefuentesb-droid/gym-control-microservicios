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

import com.gym.rutina_service.dto.RutinaDTO;
import com.gym.rutina_service.service.RutinaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/rutinas")
public class RutinaController {

    @Autowired
    private RutinaService rutinaService;

    @GetMapping
    public ResponseEntity<List<RutinaDTO>> listarTodas() {
        List<RutinaDTO> rutinas = rutinaService.obtenerTodas();

        if (rutinas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rutinas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutinaDTO> buscarPorId(@PathVariable Integer id) {
        try {
            RutinaDTO dto = rutinaService.buscarPorId(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<RutinaDTO> crearRutina(@Valid @RequestBody RutinaDTO dto) {
        try {
            RutinaDTO nueva = rutinaService.guardar(dto);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RutinaDTO> actualizarRutina(@PathVariable Integer id,
                                                      @RequestBody RutinaDTO dto) {
        try {
            RutinaDTO actualizada = rutinaService.actualizar(id, dto);
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRutina(@PathVariable Integer id) {
        try {
            String resultado = rutinaService.eliminar(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RutinaDTO>> buscarPorEstado(@PathVariable Boolean estado) {
        List<RutinaDTO> rutinas = rutinaService.buscarPorEstado(estado);

        if (rutinas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rutinas, HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<RutinaDTO>> buscarPorNombre(@PathVariable String nombre) {
        List<RutinaDTO> rutinas = rutinaService.buscarPorNombre(nombre);

        if (rutinas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rutinas, HttpStatus.OK);
    }

    @GetMapping("/socio/{idSocio}")
    public ResponseEntity<List<RutinaDTO>> buscarPorSocio(@PathVariable Integer idSocio) {
        List<RutinaDTO> rutinas = rutinaService.buscarPorSocio(idSocio);

        if (rutinas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rutinas, HttpStatus.OK);
    }

    @GetMapping("/entrenador/{idEntrenador}")
    public ResponseEntity<List<RutinaDTO>> buscarPorEntrenador(@PathVariable Integer idEntrenador) {
        List<RutinaDTO> rutinas = rutinaService.buscarPorEntrenador(idEntrenador);

        if (rutinas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rutinas, HttpStatus.OK);
    }
}
