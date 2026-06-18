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
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gym.socio.dto.SocioDTO;
import com.gym.socio.model.Socio;
import com.gym.socio.service.SocioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Socios", description = "Operaciones para socios")
@RestController
@RequestMapping("/api/v1/socios")
public class SocioController {

    @Autowired
    private SocioService socioService;

    @Operation(
    summary = "Listar todos los socios",
    description = "Obtiene todos los socios  registrados en la base de datos"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de socios obtenida correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
               value = """
                [
                    {
                    "id": 1,
                    "rut": "12345678-9",
                    "nombre": "Leo",
                    "apellido": "Fuentes",
                    "correo": "leo@gmail.com",
                    "telefono": "912345678",
                    "direccion": "Maipu",
                    "fechaNacimiento": "2000-05-10",
                    "estado": true
                    }
                ]
                """
            )
        )
    )
    @GetMapping
    public ResponseEntity<List<SocioDTO>> todosLosUsuario() {
        List<SocioDTO> socios = socioService.obtenerTodos();
        if (socios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(socios, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SocioDTO> buscarPorId(@PathVariable Integer id) {
        try {
            SocioDTO soci = socioService.buscarPorId(id);
            return new ResponseEntity<>(soci, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SocioDTO> agregarSocio(@Valid @RequestBody Socio soci) {
        try {
            Socio guardado = socioService.guardarSocio(soci);
            SocioDTO dto = socioService.buscarPorId(guardado.getId());

            return new ResponseEntity<>(dto, HttpStatus.CREATED);} 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocioDTO> actualizarSocio(@PathVariable Integer id, @RequestBody Socio soci){
        try{
                Socio newSoci = socioService.actualizarSocio(id, soci);
                SocioDTO dto = socioService.buscarPorId(newSoci.getId());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarSocio(@PathVariable Integer id) {
        String resultado = socioService.eliminar(id);
        // Si el mensaje contiene "exitosamente", es un éxito
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SocioDTO>> buscarPorEstado(@PathVariable Boolean estado) {
        List<SocioDTO> socios = socioService.buscarPorEstado(estado);
        if (socios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(socios, HttpStatus.OK);
    }

}
