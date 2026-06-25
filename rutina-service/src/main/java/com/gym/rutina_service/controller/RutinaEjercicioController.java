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

import com.gym.rutina_service.dto.RutinaEjercicioDTO;
import com.gym.rutina_service.model.RutinaEjercicio;
import com.gym.rutina_service.service.RutinaEjercicioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Rutina Ejercicios", description = "Operaciones relacionadas con los ejercicios asignados a cada rutina")
@RestController
@RequestMapping("/api/v1/rutina-ejercicios")
public class RutinaEjercicioController {

    @Autowired
    private RutinaEjercicioService service;

    @Operation(
        summary = "Listar ejercicios de las rutinas",
        description = "Obtiene todas las relaciones registradas entre rutinas y ejercicios"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista obtenida correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = """
                [
                    {
                        "id": 1,
                        "series": 3,
                        "repeticiones": 12,
                        "descansoSegundos": 60,
                        "orden": 1,
                        "observaciones": "Mantener la espalda recta",
                        "rutinaCabeceraId": 1,
                        "ejercicioId": 1
                    }
                ]
                """
            )
        )
    )
    @GetMapping
    public ResponseEntity<List<RutinaEjercicioDTO>> listar() {
        List<RutinaEjercicioDTO> lista = service.obtenerTodos();
        if(lista.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar ejercicio de rutina por ID",
        description = "Obtiene una relación específica entre una rutina y un ejercicio"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Relación encontrada"),
        @ApiResponse(responseCode = "404", description = "Relación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RutinaEjercicioDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(service.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Asignar un ejercicio a una rutina",
        description = "Registra un ejercicio con sus series, repeticiones, descanso y orden dentro de una rutina"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ejercicio asignado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<RutinaEjercicioDTO> crear(@Valid @RequestBody RutinaEjercicio rutina) {
        try {
            return new ResponseEntity<>(service.crear(rutina), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Actualizar un ejercicio de rutina",
        description = "Actualiza las series, repeticiones, descanso, orden u observaciones de un ejercicio asignado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignación actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Asignación no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RutinaEjercicio> actualizar(@PathVariable Integer id, @RequestBody RutinaEjercicio rutina) {
        try {
            return new ResponseEntity<>(service.actualizar(id, rutina), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Eliminar un ejercicio de una rutina",
        description = "Elimina la relación entre una rutina y un ejercicio"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Relación eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Relación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(service.eliminar(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
