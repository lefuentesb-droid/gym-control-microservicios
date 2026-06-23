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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Ejercicios", description = "Operaciones relacionadas con el catálogo de ejercicios")
@RestController
@RequestMapping("/api/v1/ejercicios")
public class EjercicioController {

    @Autowired
    private EjercicioService ejercicioService;

    @Operation(
        summary = "Listar todos los ejercicios",
        description = "Obtiene todos los ejercicios registrados en el catálogo"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de ejercicios obtenida correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = """
                [
                    {
                        "id": 1,
                        "nombre": "Sentadilla",
                        "descripcion": "Ejercicio de fuerza para tren inferior",
                        "grupoMuscular": "Piernas",
                        "dificultad": "MEDIA",
                        "videoUrl": null,
                        "estado": true
                    }
                ]
                """
            )
        )
    )
    @GetMapping
    public ResponseEntity<List<EjercicioDTO>> listarTodos() {
        List<EjercicioDTO> ejercicios = ejercicioService.obtenerTodos();
        if (ejercicios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ejercicios, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar ejercicio por ID",
        description = "Obtiene un ejercicio específico según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ejercicio encontrado"),
        @ApiResponse(responseCode = "404", description = "Ejercicio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EjercicioDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(ejercicioService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Crear un ejercicio",
        description = "Registra un nuevo ejercicio en el catálogo del gimnasio"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ejercicio creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EjercicioDTO> crearEjercicio(@Valid @RequestBody Ejercicio ejercicio) {
        return new ResponseEntity<>(ejercicioService.crear(ejercicio), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Actualizar un ejercicio",
        description = "Actualiza los datos de un ejercicio existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ejercicio actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Ejercicio no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Ejercicio> actualizarEjercicio(@PathVariable Integer id, @RequestBody Ejercicio ejercicio) {
        try {
            return new ResponseEntity<>(ejercicioService.actualizar(id, ejercicio), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Deshabilitar un ejercicio",
        description = "Cambia el estado del ejercicio a false (eliminación lógica)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ejercicio deshabilitado correctamente"),
        @ApiResponse(responseCode = "404", description = "Ejercicio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEjercicio(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(ejercicioService.eliminar(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
