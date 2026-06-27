package com.gym.pago.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gym.pago.dto.PagoDTO;
import com.gym.pago.model.Pago;
import com.gym.pago.service.PagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoDTO>> obtenerTodos() {
        List<PagoDTO> pagos = pagoService.obtenerTodos();

        if (pagos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> buscarPorId(@PathVariable Integer id) {
        try {
            PagoDTO pago = pagoService.buscarPorId(id);
            return new ResponseEntity<>(pago, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Pago pago) {
        try {
            Pago guardado = pagoService.guardar(pago);
            PagoDTO dto = pagoService.buscarPorId(guardado.getId());

            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Pago pago) {
        try {
            Pago actualizado = pagoService.actualizar(id, pago);
            PagoDTO dto = pagoService.buscarPorId(actualizado.getId());

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        String resultado = pagoService.eliminar(id);

        if (resultado.contains("correctamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }

        return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/socio/{socioId}")
    public ResponseEntity<List<PagoDTO>> buscarPorSocioId(@PathVariable Integer socioId) {
        List<PagoDTO> pagos = pagoService.buscarPorSocioId(socioId);

        if (pagos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PagoDTO>> buscarPorEstado(@PathVariable String estado) {
        List<PagoDTO> pagos = pagoService.buscarPorEstado(estado);

        if (pagos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }
}