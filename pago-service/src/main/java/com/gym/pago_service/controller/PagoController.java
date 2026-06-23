package com.gym.pago_service.controller;

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

import com.gym.pago_service.dto.PagoDTO;
import com.gym.pago_service.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Pagos", description = "Operaciones relacionadas con los pagos de los socios")
@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(
        summary = "Listar todos los pagos",
        description = "Obtiene todos los pagos registrados en la base de datos"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de pagos obtenida correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = """
                [
                    {
                        "id": 1,
                        "monto": 29990.0,
                        "fechaPago": "2025-06-01",
                        "comprobante": "CMP-0001",
                        "estado": true,
                        "idSocio": 1,
                        "idMembresia": 1,
                        "idMetodoPago": 1,
                        "nombreMetodoPago": "Tarjeta de crédito"
                    }
                ]
                """
            )
        )
    )
    @GetMapping
    public ResponseEntity<List<PagoDTO>> listarPagos() {
        List<PagoDTO> pagos = pagoService.obtenerTodos();
        if (pagos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar pago por ID",
        description = "Obtiene un pago específico según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(pagoService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Registrar un pago",
        description = "Crea un nuevo pago en el sistema. El id_socio y id_membresia son validados contra socio-service vía REST."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pago registrado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o método de pago no encontrado")
    })
    @PostMapping
    public ResponseEntity<PagoDTO> crearPago(@Valid @RequestBody PagoDTO dto) {
        try {
            return new ResponseEntity<>(pagoService.registrarPago(dto), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Actualizar un pago",
        description = "Actualiza los datos de un pago existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> actualizarPago(@PathVariable Integer id, @RequestBody PagoDTO dto) {
        try {
            return new ResponseEntity<>(pagoService.actualizarPago(id, dto), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Deshabilitar un pago",
        description = "Cambia el estado del pago a false (eliminación lógica)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago deshabilitado correctamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPago(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(pagoService.eliminar(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Buscar pagos por socio",
        description = "Obtiene todos los pagos realizados por un socio específico"
    )
    @GetMapping("/socio/{idSocio}")
    public ResponseEntity<List<PagoDTO>> buscarPorSocio(@PathVariable Integer idSocio) {
        List<PagoDTO> pagos = pagoService.buscarPorSocio(idSocio);
        if (pagos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar pagos por estado",
        description = "Obtiene todos los pagos según su estado (true=activo, false=inactivo)"
    )
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PagoDTO>> buscarPorEstado(@PathVariable Boolean estado) {
        List<PagoDTO> pagos = pagoService.buscarPorEstado(estado);
        if (pagos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }
}
