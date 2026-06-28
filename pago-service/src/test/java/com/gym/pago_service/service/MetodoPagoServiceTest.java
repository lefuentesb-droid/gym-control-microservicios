package com.gym.pago_service.service;

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

import com.gym.pago_service.dto.MetodoPagoDTO;
import com.gym.pago_service.model.MetodoPago;
import com.gym.pago_service.repository.MetodoPagoRepository;

@ExtendWith(MockitoExtension.class)
class MetodoPagoServiceTest {

    @Mock
    private MetodoPagoRepository metodoPagoRepository;

    @InjectMocks
    private MetodoPagoService metodoPagoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        // GIVEN
        when(metodoPagoRepository.findAll()).thenReturn(List.of(crearMetodoPago(1)));

        // WHEN
        List<MetodoPagoDTO> resultado = metodoPagoService.obtenerTodos();

        // THEN
        assertEquals(1, resultado.size());
        verify(metodoPagoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorIdExitoso() {
        // GIVEN
        Integer id = 1;
        MetodoPago metodoPago = crearMetodoPago(id);
        when(metodoPagoRepository.findById(id)).thenReturn(Optional.of(metodoPago));

        // WHEN
        MetodoPagoDTO resultado = metodoPagoService.buscarPorId(id);

        // THEN
        assertNotNull(resultado);
        assertEquals("Tarjeta", resultado.getNombre());
        verify(metodoPagoRepository, times(1)).findById(id);
    }

    @Test
    void testGuardarMetodoPago() {
        // GIVEN
        MetodoPago metodoPago = crearMetodoPago(null);
        MetodoPago guardado = crearMetodoPago(1);
        when(metodoPagoRepository.save(metodoPago)).thenReturn(guardado);

        // WHEN
        MetodoPagoDTO resultado = metodoPagoService.guardar(metodoPago);

        // THEN
        assertNotNull(resultado);
        assertEquals(Integer.valueOf(1), resultado.getIdMetodoPago());
        verify(metodoPagoRepository, times(1)).save(metodoPago);
    }

    @Test
    void testActualizarMetodoPago() {
        // GIVEN
        Integer id = 1;
        MetodoPago existente = crearMetodoPago(id);
        MetodoPago nuevosDatos = new MetodoPago();
        nuevosDatos.setNombre("Efectivo");
        nuevosDatos.setDescripcion("Pago en caja");
        nuevosDatos.setEstado(true);
        when(metodoPagoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(metodoPagoRepository.save(existente)).thenReturn(existente);

        // WHEN
        MetodoPago resultado = metodoPagoService.actualizar(id, nuevosDatos);

        // THEN
        assertEquals("Efectivo", resultado.getNombre());
        verify(metodoPagoRepository, times(1)).save(existente);
    }

    @Test
    void testEliminarMetodoPagoLogicamente() {
        // GIVEN
        Integer id = 1;
        MetodoPago metodoPago = crearMetodoPago(id);
        when(metodoPagoRepository.findById(id)).thenReturn(Optional.of(metodoPago));
        when(metodoPagoRepository.save(metodoPago)).thenReturn(metodoPago);

        // WHEN
        String resultado = metodoPagoService.eliminar(id);

        // THEN
        assertEquals(false, metodoPago.getEstado());
        assertEquals("Método de pago deshabilitado correctamente", resultado);
        verify(metodoPagoRepository, times(1)).save(metodoPago);
    }

    @Test
    void testBuscarPorNombre() {
        // GIVEN
        when(metodoPagoRepository.findByNombre("Tarjeta")).thenReturn(List.of(crearMetodoPago(1)));

        // WHEN
        List<MetodoPagoDTO> resultado = metodoPagoService.buscarPorNombre("Tarjeta");

        // THEN
        assertEquals(1, resultado.size());
        verify(metodoPagoRepository, times(1)).findByNombre("Tarjeta");
    }

    @Test
    void testBuscarPorEstado() {
        // GIVEN
        when(metodoPagoRepository.findByEstado(true)).thenReturn(List.of(crearMetodoPago(1)));

        // WHEN
        List<MetodoPagoDTO> resultado = metodoPagoService.buscarPorEstado(true);

        // THEN
        assertEquals(1, resultado.size());
        verify(metodoPagoRepository, times(1)).findByEstado(true);
    }

    private MetodoPago crearMetodoPago(Integer id) {
        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setIdMetodoPago(id);
        metodoPago.setNombre("Tarjeta");
        metodoPago.setDescripcion("Pago con tarjeta");
        metodoPago.setEstado(true);
        return metodoPago;
    }
}
