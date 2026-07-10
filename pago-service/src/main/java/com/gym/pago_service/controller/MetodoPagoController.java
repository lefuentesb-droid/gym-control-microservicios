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

import com.gym.pago_service.dto.MetodoPagoDTO;
import com.gym.pago_service.model.MetodoPago;
import com.gym.pago_service.service.MetodoPagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Métodos de Pago", description = "Operaciones relacionadas con los métodos de pago disponibles")
@RestController
@RequestMapping("/api/v1/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @Operation(
        summary = "Listar todos los métodos de pago",
        description = "Obtiene todos los métodos de pago registrados en la base de datos"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de métodos de pago obtenida correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = """
                [
                    {
                        "idMetodoPago": 1,
                        "nombre": "Tarjeta de crédito",
                        "descripcion": "Pago con tarjeta de crédito Visa/Mastercard",
                        "estado": true
                    }
                ]
                """
            )
        )
    )
    @GetMapping
    public ResponseEntity<List<MetodoPagoDTO>> listarMetodosPago() {
        List<MetodoPagoDTO> metodos = metodoPagoService.obtenerTodos();
        if (metodos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(metodos, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar método de pago por ID",
        description = "Obtiene un método de pago específico según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Método de pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MetodoPagoDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(metodoPagoService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Crear un método de pago",
        description = "Registra un nuevo método de pago en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Método de pago creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<MetodoPagoDTO> crearMetodoPago(@Valid @RequestBody MetodoPago metodoPago) {
        try {
            return new ResponseEntity<>(metodoPagoService.guardar(metodoPago), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Actualizar un método de pago",
        description = "Actualiza los datos de un método de pago existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Método de pago actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagoDTO> actualizarMetodoPago(@PathVariable Integer id,
                                                               @RequestBody MetodoPago metodoPago) {
        try {
            MetodoPago actualizado = metodoPagoService.actualizar(id, metodoPago);
            MetodoPagoDTO dto = metodoPagoService.buscarPorId(actualizado.getIdMetodoPago());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Deshabilitar un método de pago",
        description = "Cambia el estado del método de pago a false (eliminación lógica)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Método de pago deshabilitado correctamente"),
        @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMetodoPago(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(metodoPagoService.eliminar(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Buscar métodos de pago por nombre",
        description = "Obtiene todos los métodos de pago que coinciden con el nombre indicado"
    )
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<MetodoPagoDTO>> buscarPorNombre(@PathVariable String nombre) {
        List<MetodoPagoDTO> metodos = metodoPagoService.buscarPorNombre(nombre);
        if (metodos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(metodos, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar métodos de pago por estado",
        description = "Obtiene todos los métodos de pago según su estado (true=activo, false=inactivo)"
    )
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MetodoPagoDTO>> buscarPorEstado(@PathVariable Boolean estado) {
        List<MetodoPagoDTO> metodos = metodoPagoService.buscarPorEstado(estado);
        if (metodos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(metodos, HttpStatus.OK);
    }
}
