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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Rutinas", description = "Operaciones relacionadas con las rutinas asignadas a los socios")
@RestController
@RequestMapping("/api/v1/rutinas")
public class RutinaController {

    @Autowired
    private RutinaService rutinaService;

    @Operation(
        summary = "Listar todas las rutinas",
        description = "Obtiene todas las rutinas registradas en la base de datos"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de rutinas obtenida correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = """
                [
                    {
                        "id": 1,
                        "idSocio": 1,
                        "nombreSocio": null,
                        "idEntrenador": 1,
                        "nombreEntrenador": null,
                        "nombre": "Rutina de iniciacion",
                        "descripcion": "Rutina basica para principiantes",
                        "fechaAsignacion": "2026-06-24",
                        "estado": true
                    }
                ]
                """
            )
        )
    )
    @GetMapping
    public ResponseEntity<List<RutinaDTO>> listarTodas() {
        List<RutinaDTO> rutinas = rutinaService.obtenerTodas();

        if (rutinas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rutinas, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar rutina por ID",
        description = "Obtiene una rutina específica según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rutina encontrada"),
        @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RutinaDTO> buscarPorId(@PathVariable Integer id) {
        try {
            RutinaDTO dto = rutinaService.buscarPorId(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Crear una rutina",
        description = "Registra una nueva rutina asociada a un socio y un entrenador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Rutina creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<RutinaDTO> crearRutina(@Valid @RequestBody RutinaDTO dto) {
        try {
            RutinaDTO nueva = rutinaService.guardar(dto);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Actualizar una rutina",
        description = "Actualiza los datos de una rutina existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rutina actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RutinaDTO> actualizarRutina(@PathVariable Integer id, @RequestBody RutinaDTO dto) {
        try {
            RutinaDTO actualizada = rutinaService.actualizar(id, dto);
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Deshabilitar una rutina",
        description = "Cambia el estado de la rutina a false mediante eliminación lógica"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rutina deshabilitada correctamente"),
        @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRutina(@PathVariable Integer id) {
        try {
            String resultado = rutinaService.eliminar(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Buscar rutinas por estado",
        description = "Obtiene las rutinas según su estado: true para activas y false para inactivas"
    )
    @ApiResponse(responseCode = "200", description = "Rutinas obtenidas correctamente")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RutinaDTO>> buscarPorEstado(@PathVariable Boolean estado) {
        List<RutinaDTO> rutinas = rutinaService.buscarPorEstado(estado);

        if (rutinas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rutinas, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar rutinas por nombre",
        description = "Obtiene las rutinas que coinciden con el nombre indicado"
    )
    @ApiResponse(responseCode = "200", description = "Rutinas obtenidas correctamente")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<RutinaDTO>> buscarPorNombre(@PathVariable String nombre) {
        List<RutinaDTO> rutinas = rutinaService.buscarPorNombre(nombre);

        if (rutinas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rutinas, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar rutinas por socio",
        description = "Obtiene todas las rutinas asignadas al socio indicado"
    )
    @ApiResponse(responseCode = "200", description = "Rutinas del socio obtenidas correctamente")
    @GetMapping("/socio/{idSocio}")
    public ResponseEntity<List<RutinaDTO>> buscarPorSocio(@PathVariable Integer idSocio) {
        List<RutinaDTO> rutinas = rutinaService.buscarPorSocio(idSocio);

        if (rutinas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rutinas, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar rutinas por entrenador",
        description = "Obtiene todas las rutinas asignadas por el entrenador indicado"
    )
    @ApiResponse(responseCode = "200", description = "Rutinas del entrenador obtenidas correctamente")
    @GetMapping("/entrenador/{idEntrenador}")
    public ResponseEntity<List<RutinaDTO>> buscarPorEntrenador(@PathVariable Integer idEntrenador) {
        List<RutinaDTO> rutinas = rutinaService.buscarPorEntrenador(idEntrenador);

        if (rutinas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(rutinas, HttpStatus.OK);
    }
}
