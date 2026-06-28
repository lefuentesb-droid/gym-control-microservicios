package com.gym.socio.controller;

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

import com.gym.socio.dto.MembresiaDTO;
import com.gym.socio.service.MembresiaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/membresia")
public class MembresiaController {

    @Autowired
    private MembresiaService membresiaService;

    
    @GetMapping
    public ResponseEntity<List<MembresiaDTO>> listarMembrecias() {
        List<MembresiaDTO> membresias = membresiaService.obtenerTodos();
        return membresias.isEmpty() 
            ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
            : new ResponseEntity<>(membresias, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MembresiaDTO> buscarPorId(@PathVariable Integer id) {
        try {
            MembresiaDTO dto = membresiaService.buscarPorId(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMembresia(@PathVariable Integer id) {
        String resultado = membresiaService.eliminarMembresia(id);

        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MembresiaDTO> crearMembresia(@Valid @RequestBody MembresiaDTO dto) {
        MembresiaDTO nueva = membresiaService.guardar(dto);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }
            
    
    @PutMapping("/{socioId}/membresia/{membresiaId}")
    public ResponseEntity<String> agregarmembresia(@PathVariable Integer membresiaId, @PathVariable Integer socioId) {
        try {
            String resultado = membresiaService.asignarMembresiaSocio(socioId, membresiaId);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{membresiaId}/socio/{socioId}")
    public ResponseEntity<String> EliminarMembresiaSocio(@PathVariable Integer membresiaId, @PathVariable Integer socioId) {
        try {
            String resultado = membresiaService.eliminarMembresiaSocio(membresiaId, socioId);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MembresiaDTO>> buscarPorEstado(@PathVariable String estado) {
        List<MembresiaDTO> membresias = membresiaService.buscarPorEstado(estado);

        if (membresias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(membresias, HttpStatus.OK);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MembresiaDTO>> buscarPorTipo(@PathVariable String tipo) {
        List<MembresiaDTO> membresias = membresiaService.buscarPorTipo(tipo);

        if (membresias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(membresias, HttpStatus.OK);
    }

    @GetMapping("/socio/{idSocio}")
    public ResponseEntity<List<MembresiaDTO>> buscarPorSocio(@PathVariable Integer idSocio) {
        List<MembresiaDTO> membresias = membresiaService.buscarPorSocio(idSocio);

        if (membresias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(membresias, HttpStatus.OK);
    }
    
}    
