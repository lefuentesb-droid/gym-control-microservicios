package com.gym.rutina_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gym.rutina_service.dto.EjercicioDTO;
import com.gym.rutina_service.model.Ejercicio;
import com.gym.rutina_service.repository.EjercicioRepository;

@ExtendWith(MockitoExtension.class)
class EjercicioServiceTest {

    @Mock
    private EjercicioRepository ejercicioRepository;

    @InjectMocks
    private EjercicioService ejercicioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        // GIVEN
        when(ejercicioRepository.findAll()).thenReturn(List.of(crearEjercicio(1)));

        // WHEN
        List<EjercicioDTO> resultado = ejercicioService.obtenerTodos();

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(ejercicioRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorIdExitoso() {
        // GIVEN
        Integer id = 1;
        Ejercicio ejercicio = crearEjercicio(id);
        when(ejercicioRepository.findById(id)).thenReturn(Optional.of(ejercicio));

        // WHEN
        EjercicioDTO resultado = ejercicioService.buscarPorId(id);

        // THEN
        assertNotNull(resultado);
        assertEquals("Sentadilla", resultado.getNombre());
        verify(ejercicioRepository, times(1)).findById(id);
    }

    @Test
    void testCrearEjercicio() {
        // GIVEN
        Ejercicio ejercicio = crearEjercicio(null);
        Ejercicio guardado = crearEjercicio(1);
        when(ejercicioRepository.save(ejercicio)).thenReturn(guardado);

        // WHEN
        EjercicioDTO resultado = ejercicioService.crear(ejercicio);

        // THEN
        assertNotNull(resultado);
        assertEquals(true, resultado.getEstado());
        verify(ejercicioRepository, times(1)).save(ejercicio);
    }

    @Test
    void testActualizarEjercicio() {
        // GIVEN
        Integer id = 1;
        Ejercicio existente = crearEjercicio(id);
        Ejercicio nuevosDatos = new Ejercicio();
        nuevosDatos.setNombre("Prensa");
        nuevosDatos.setDescripcion("Ejercicio actualizado");
        nuevosDatos.setGrupoMuscular("Piernas");
        nuevosDatos.setDificultad("ALTA");
        nuevosDatos.setVideoUrl("video");
        nuevosDatos.setEstado(true);
        when(ejercicioRepository.findById(id)).thenReturn(Optional.of(existente));
        when(ejercicioRepository.save(existente)).thenReturn(existente);

        // WHEN
        Ejercicio resultado = ejercicioService.actualizar(id, nuevosDatos);

        // THEN
        assertEquals("Prensa", resultado.getNombre());
        assertEquals("ALTA", resultado.getDificultad());
        verify(ejercicioRepository, times(1)).save(existente);
    }

    @Test
    void testEliminarEjercicioLogicamente() {
        // GIVEN
        Integer id = 1;
        Ejercicio ejercicio = crearEjercicio(id);
        when(ejercicioRepository.findById(id)).thenReturn(Optional.of(ejercicio));
        when(ejercicioRepository.save(ejercicio)).thenReturn(ejercicio);

        // WHEN
        String resultado = ejercicioService.eliminar(id);

        // THEN
        assertEquals(false, ejercicio.getEstado());
        assertEquals("Ejercicio deshabilitado correctamente", resultado);
        verify(ejercicioRepository, times(1)).save(ejercicio);
    }

    private Ejercicio crearEjercicio(Integer id) {
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId(id);
        ejercicio.setNombre("Sentadilla");
        ejercicio.setDescripcion("Ejercicio de piernas");
        ejercicio.setGrupoMuscular("Piernas");
        ejercicio.setDificultad("MEDIA");
        ejercicio.setEstado(true);
        return ejercicio;
    }
}
