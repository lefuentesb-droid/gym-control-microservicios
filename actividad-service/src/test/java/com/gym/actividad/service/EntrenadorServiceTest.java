package com.gym.actividad.service;

import com.gym.actividad.dto.EntrenadorDTO;
import com.gym.actividad.model.Entrenador;
import com.gym.actividad.repository.EntrenadorRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EntrenadorServiceTest {

    @Mock
    private EntrenadorRepository entrenadorRepository;

    @InjectMocks
    private EntrenadorService entrenadorService;

    @Test
    void crearEntrenador_GuardarEntrenadorConEstadoTrueYFechaRegistro() {
    
        Entrenador entrenador = new Entrenador();
        entrenador.setNombre("Carlos");
        entrenador.setApellido("Rojas");
        entrenador.setEspecialidad("Musculación");
        entrenador.setCorreo("carlos.rojas@gym.cl");
        entrenador.setTelefono("912345678");
        Entrenador entrenadorGuardado = new Entrenador();
        entrenadorGuardado.setId(1);
        entrenadorGuardado.setNombre("Carlos");
        entrenadorGuardado.setApellido("Rojas");
        entrenadorGuardado.setEspecialidad("Musculación");
        entrenadorGuardado.setCorreo("carlos.rojas@gym.cl");
        entrenadorGuardado.setTelefono("912345678");
        entrenadorGuardado.setEstado(true);
        entrenadorGuardado.setFechaRegistro(LocalDate.now());
        when(entrenadorRepository.save(any(Entrenador.class))).thenReturn(entrenadorGuardado);

        EntrenadorDTO resultado = entrenadorService.crearEntrenador(entrenador);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("Rojas", resultado.getApellido());
        assertEquals("Musculación", resultado.getEspecialidad());
        assertEquals("carlos.rojas@gym.cl", resultado.getCorreo());
        assertEquals("912345678", resultado.getTelefono());
        assertTrue(resultado.getEstado());
        assertNotNull(resultado.getFechaRegistro());

        verify(entrenadorRepository, times(1)).save(any(Entrenador.class));
    }
}