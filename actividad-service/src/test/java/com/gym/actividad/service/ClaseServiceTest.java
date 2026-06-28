package com.gym.actividad.service;

import com.gym.actividad.dto.ClaseDTO;
import com.gym.actividad.model.Clase;
import com.gym.actividad.model.Entrenador;
import com.gym.actividad.repository.ClaseRepository;
import com.gym.actividad.repository.EntrenadorRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClaseServiceTest {

    @Mock
    private ClaseRepository claseRepository;

    @Mock
    private EntrenadorRepository entrenadorRepository;

    @InjectMocks
    private ClaseService claseService;

    @Test
    void guardar_CrearClaseCuandoExisteEntrenador() {
        // Given
        Entrenador entrenador = new Entrenador();
        entrenador.setId(2);
        entrenador.setNombre("Carlos");
        entrenador.setApellido("Rojas");
        entrenador.setEspecialidad("Musculación");
        entrenador.setCorreo("carlos.rojas@gym.cl");
        entrenador.setTelefono("912345678");
        entrenador.setEstado(true);

        ClaseDTO dto = new ClaseDTO();
        dto.setIdEntrenador(2);
        dto.setNombre("Funcional");
        dto.setDescripcion("Clase de entrenamiento funcional");
        dto.setCupoMaximo(20);
        dto.setDuracionMinutos(60);
        dto.setHorario("18:00");
        dto.setDiasSemana("Lunes, Miércoles, Viernes");
        dto.setEstado(true);

        Clase claseGuardada = new Clase();
        claseGuardada.setId(1);
        claseGuardada.setEntrenador(entrenador);
        claseGuardada.setNombre("Funcional");
        claseGuardada.setDescripcion("Clase de entrenamiento funcional");
        claseGuardada.setCupoMaximo(20);
        claseGuardada.setDuracionMinutos(60);
        claseGuardada.setHorario("18:00");
        claseGuardada.setDiasSemana("Lunes, Miércoles, Viernes");
        claseGuardada.setEstado(true);

        when(entrenadorRepository.findById(2)).thenReturn(Optional.of(entrenador));
        when(claseRepository.save(any(Clase.class))).thenReturn(claseGuardada);
        ClaseDTO resultado = claseService.guardar(dto);

      
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(2, resultado.getIdEntrenador());
        assertEquals("Carlos", resultado.getNombreEntrenador());
        assertEquals("Funcional", resultado.getNombre());
        assertEquals("Clase de entrenamiento funcional", resultado.getDescripcion());
        assertEquals(20, resultado.getCupoMaximo());
        assertEquals(60, resultado.getDuracionMinutos());
        assertEquals("18:00", resultado.getHorario());
        assertEquals("Lunes, Miércoles, Viernes", resultado.getDiasSemana());
        assertTrue(resultado.getEstado());

        verify(entrenadorRepository, times(1)).findById(2);
        verify(claseRepository, times(1)).save(any(Clase.class));
    }

    @Test
    void guardar_LanzarErrorCuandoEntrenadorNoExiste() {
        ClaseDTO dto = new ClaseDTO();
        dto.setIdEntrenador(99);
        dto.setNombre("Funcional");
        dto.setDescripcion("Clase de entrenamiento funcional");
        dto.setCupoMaximo(20);
        dto.setDuracionMinutos(60);
        dto.setHorario("18:00");
        dto.setDiasSemana("Lunes, Miércoles, Viernes");
        dto.setEstado(true);

        when(entrenadorRepository.findById(99)).thenReturn(Optional.empty());

        // When / Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            claseService.guardar(dto);
        });

        assertEquals("Entrenador no encontrado", exception.getMessage());

        verify(entrenadorRepository, times(1)).findById(99);
        verify(claseRepository, never()).save(any(Clase.class));
    }
}