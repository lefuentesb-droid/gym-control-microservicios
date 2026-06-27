package com.gym.pago.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gym.pago.dto.MetodoPagoDTO;
import com.gym.pago.model.MetodoPago;
import com.gym.pago.service.MetodoPagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<List<MetodoPagoDTO>> obtenerTodos() {
        List<MetodoPagoDTO> metodos = metodoPagoService.obtenerTodos();

        if (metodos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(metodos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPagoDTO> buscarPorId(@PathVariable Integer id) {
        try {
            MetodoPagoDTO metodoPago = metodoPagoService.buscarPorId(id);
            return new ResponseEntity<>(metodoPago, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MetodoPagoDTO> guardar(@Valid @RequestBody MetodoPago metodoPago) {
        try {
            MetodoPago guardado = metodoPagoService.guardar(metodoPago);
            MetodoPagoDTO dto = metodoPagoService.buscarPorId(guardado.getId());

            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagoDTO> actualizar(@PathVariable Integer id, @RequestBody MetodoPago metodoPago) {
        try {
            MetodoPago actualizado = metodoPagoService.actualizar(id, metodoPago);
            MetodoPagoDTO dto = metodoPagoService.buscarPorId(actualizado.getId());

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        String resultado = metodoPagoService.eliminar(id);

        if (resultado.contains("correctamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }

        return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/activo/{activo}")
    public ResponseEntity<List<MetodoPagoDTO>> buscarPorActivo(@PathVariable Boolean activo) {
        List<MetodoPagoDTO> metodos = metodoPagoService.buscarPorActivo(activo);

        if (metodos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(metodos, HttpStatus.OK);
    }
}