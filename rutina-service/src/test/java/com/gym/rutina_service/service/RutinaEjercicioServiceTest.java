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

import com.gym.rutina_service.dto.RutinaEjercicioDTO;
import com.gym.rutina_service.model.Ejercicio;
import com.gym.rutina_service.model.Rutina;
import com.gym.rutina_service.model.RutinaEjercicio;
import com.gym.rutina_service.repository.RutinaEjercicioRepository;

@ExtendWith(MockitoExtension.class)
class RutinaEjercicioServiceTest {

    @Mock
    private RutinaEjercicioRepository rutinaEjercicioRepository;

    @InjectMocks
    private RutinaEjercicioService rutinaEjercicioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        // GIVEN
        when(rutinaEjercicioRepository.findAll()).thenReturn(List.of(crearRutinaEjercicio(1)));

        // WHEN
        List<RutinaEjercicioDTO> resultado = rutinaEjercicioService.obtenerTodos();

        // THEN
        assertEquals(1, resultado.size());
        verify(rutinaEjercicioRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorIdExitoso() {
        // GIVEN
        Integer id = 1;
        RutinaEjercicio rutinaEjercicio = crearRutinaEjercicio(id);
        when(rutinaEjercicioRepository.findById(id)).thenReturn(Optional.of(rutinaEjercicio));

        // WHEN
        RutinaEjercicioDTO resultado = rutinaEjercicioService.buscarPorId(id);

        // THEN
        assertNotNull(resultado);
        assertEquals(Integer.valueOf(3), resultado.getSeries());
        verify(rutinaEjercicioRepository, times(1)).findById(id);
    }

    @Test
    void testCrearRutinaEjercicio() {
        // GIVEN
        RutinaEjercicio nuevo = crearRutinaEjercicio(null);
        RutinaEjercicio guardado = crearRutinaEjercicio(1);
        when(rutinaEjercicioRepository.save(nuevo)).thenReturn(guardado);

        // WHEN
        RutinaEjercicioDTO resultado = rutinaEjercicioService.crear(nuevo);

        // THEN
        assertNotNull(resultado);
        assertEquals(Integer.valueOf(12), resultado.getRepeticiones());
        verify(rutinaEjercicioRepository, times(1)).save(nuevo);
    }

    @Test
    void testActualizarRutinaEjercicio() {
        // GIVEN
        Integer id = 1;
        RutinaEjercicio existente = crearRutinaEjercicio(id);
        RutinaEjercicio nuevosDatos = crearRutinaEjercicio(null);
        nuevosDatos.setSeries(4);
        nuevosDatos.setRepeticiones(15);
        nuevosDatos.setDescansoSegundos(90);
        nuevosDatos.setOrden(2);
        nuevosDatos.setObservaciones("Actualizar carga");
        when(rutinaEjercicioRepository.findById(id)).thenReturn(Optional.of(existente));
        when(rutinaEjercicioRepository.save(existente)).thenReturn(existente);

        // WHEN
        RutinaEjercicio resultado = rutinaEjercicioService.actualizar(id, nuevosDatos);

        // THEN
        assertEquals(Integer.valueOf(4), resultado.getSeries());
        assertEquals(Integer.valueOf(15), resultado.getRepeticiones());
        verify(rutinaEjercicioRepository, times(1)).save(existente);
    }

    @Test
    void testEliminarRutinaEjercicio() {
        // GIVEN
        Integer id = 1;
        RutinaEjercicio rutinaEjercicio = crearRutinaEjercicio(id);
        when(rutinaEjercicioRepository.findById(id)).thenReturn(Optional.of(rutinaEjercicio));

        // WHEN
        String resultado = rutinaEjercicioService.eliminar(id);

        // THEN
        assertEquals("Rutina ejercicio eliminada exitosamente", resultado);
        verify(rutinaEjercicioRepository, times(1)).delete(rutinaEjercicio);
    }

    private RutinaEjercicio crearRutinaEjercicio(Integer id) {
        Rutina rutina = new Rutina();
        rutina.setId(1);

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId(1);

        RutinaEjercicio rutinaEjercicio = new RutinaEjercicio();
        rutinaEjercicio.setId(id);
        rutinaEjercicio.setSeries(3);
        rutinaEjercicio.setRepeticiones(12);
        rutinaEjercicio.setDescansoSegundos(60);
        rutinaEjercicio.setOrden(1);
        rutinaEjercicio.setRutina(rutina);
        rutinaEjercicio.setEjercicio(ejercicio);
        return rutinaEjercicio;
    }
}
