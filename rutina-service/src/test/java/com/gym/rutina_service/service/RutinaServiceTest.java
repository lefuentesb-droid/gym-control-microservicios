package com.gym.rutina_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gym.rutina_service.dto.RutinaDTO;
import com.gym.rutina_service.model.Rutina;
import com.gym.rutina_service.repository.RutinaRepository;

@ExtendWith(MockitoExtension.class)
class RutinaServiceTest {

    @Mock
    private RutinaRepository rutinaRepository;

    @InjectMocks
    private RutinaService rutinaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodas() {
        // GIVEN
        when(rutinaRepository.findAll()).thenReturn(List.of(crearRutina(1)));

        // WHEN
        List<RutinaDTO> resultado = rutinaService.obtenerTodas();

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(rutinaRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorIdExitoso() {
        // GIVEN
        Integer id = 1;
        Rutina rutina = crearRutina(id);
        when(rutinaRepository.findById(id)).thenReturn(Optional.of(rutina));

        // WHEN
        RutinaDTO resultado = rutinaService.buscarPorId(id);

        // THEN
        assertNotNull(resultado);
        assertEquals("Rutina inicial", resultado.getNombre());
        verify(rutinaRepository, times(1)).findById(id);
    }

    @Test
    void testGuardarRutina() {
        // GIVEN
        RutinaDTO dto = crearRutinaDTO();
        Rutina nueva = crearRutina(null);
        Rutina guardada = crearRutina(1);
        when(rutinaRepository.save(nueva)).thenReturn(guardada);

        // WHEN
        RutinaDTO resultado = rutinaService.guardar(dto);

        // THEN
        assertNotNull(resultado);
        assertEquals(Integer.valueOf(1), resultado.getIdSocio());
        verify(rutinaRepository, times(1)).save(nueva);
    }

    @Test
    void testActualizarRutina() {
        // GIVEN
        Integer id = 1;
        Rutina existente = crearRutina(id);
        RutinaDTO nuevosDatos = crearRutinaDTO();
        nuevosDatos.setNombre("Rutina avanzada");
        when(rutinaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(rutinaRepository.save(existente)).thenReturn(existente);

        // WHEN
        RutinaDTO resultado = rutinaService.actualizar(id, nuevosDatos);

        // THEN
        assertEquals("Rutina avanzada", resultado.getNombre());
        verify(rutinaRepository, times(1)).save(existente);
    }

    @Test
    void testEliminarRutinaLogicamente() {
        // GIVEN
        Integer id = 1;
        Rutina rutina = crearRutina(id);
        when(rutinaRepository.findById(id)).thenReturn(Optional.of(rutina));
        when(rutinaRepository.save(rutina)).thenReturn(rutina);

        // WHEN
        String resultado = rutinaService.eliminar(id);

        // THEN
        assertEquals(false, rutina.getEstado());
        assertEquals("Rutina deshabilitada correctamente", resultado);
        verify(rutinaRepository, times(1)).save(rutina);
    }

    @Test
    void testBuscarPorEstado() {
        // GIVEN
        when(rutinaRepository.findByEstado(true)).thenReturn(List.of(crearRutina(1)));

        // WHEN
        List<RutinaDTO> resultado = rutinaService.buscarPorEstado(true);

        // THEN
        assertEquals(1, resultado.size());
        verify(rutinaRepository, times(1)).findByEstado(true);
    }

    @Test
    void testBuscarPorNombre() {
        // GIVEN
        when(rutinaRepository.findByNombre("Rutina inicial")).thenReturn(List.of(crearRutina(1)));

        // WHEN
        List<RutinaDTO> resultado = rutinaService.buscarPorNombre("Rutina inicial");

        // THEN
        assertEquals(1, resultado.size());
        verify(rutinaRepository, times(1)).findByNombre("Rutina inicial");
    }

    @Test
    void testBuscarPorSocio() {
        // GIVEN
        when(rutinaRepository.findByIdSocio(1)).thenReturn(List.of(crearRutina(1)));

        // WHEN
        List<RutinaDTO> resultado = rutinaService.buscarPorSocio(1);

        // THEN
        assertEquals(1, resultado.size());
        verify(rutinaRepository, times(1)).findByIdSocio(1);
    }

    @Test
    void testBuscarPorEntrenador() {
        // GIVEN
        when(rutinaRepository.findByIdEntrenador(1)).thenReturn(List.of(crearRutina(1)));

        // WHEN
        List<RutinaDTO> resultado = rutinaService.buscarPorEntrenador(1);

        // THEN
        assertEquals(1, resultado.size());
        verify(rutinaRepository, times(1)).findByIdEntrenador(1);
    }

    private Rutina crearRutina(Integer id) {
        Rutina rutina = new Rutina();
        rutina.setId(id);
        rutina.setIdSocio(1);
        rutina.setIdEntrenador(1);
        rutina.setNombre("Rutina inicial");
        rutina.setDescripcion("Rutina para principiantes");
        rutina.setFechaAsignacion(LocalDate.now());
        rutina.setEstado(true);
        return rutina;
    }

    private RutinaDTO crearRutinaDTO() {
        RutinaDTO dto = new RutinaDTO();
        dto.setIdSocio(1);
        dto.setIdEntrenador(1);
        dto.setNombre("Rutina inicial");
        dto.setDescripcion("Rutina para principiantes");
        dto.setFechaAsignacion(LocalDate.now());
        dto.setEstado(true);
        return dto;
    }
}
