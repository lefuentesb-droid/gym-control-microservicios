package com.gym.pago_service.service;

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

import com.gym.pago_service.dto.PagoDTO;
import com.gym.pago_service.model.MetodoPago;
import com.gym.pago_service.model.Pago;
import com.gym.pago_service.repository.MetodoPagoRepository;
import com.gym.pago_service.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private MetodoPagoRepository metodoPagoRepository;

    @InjectMocks
    private PagoService pagoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        // GIVEN
        when(pagoRepository.findAll()).thenReturn(List.of(crearPago(1)));

        // WHEN
        List<PagoDTO> resultado = pagoService.obtenerTodos();

        // THEN
        assertEquals(1, resultado.size());
        verify(pagoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorIdExitoso() {
        // GIVEN
        Integer id = 1;
        Pago pago = crearPago(id);
        when(pagoRepository.findById(id)).thenReturn(Optional.of(pago));

        // WHEN
        PagoDTO resultado = pagoService.buscarPorId(id);

        // THEN
        assertNotNull(resultado);
        assertEquals(Double.valueOf(29990), resultado.getMonto());
        verify(pagoRepository, times(1)).findById(id);
    }

    @Test
    void testRegistrarPago() {
        // GIVEN
        PagoDTO dto = crearPagoDTO();
        MetodoPago metodoPago = crearMetodoPago();
        Pago nuevo = crearPago(null);
        Pago guardado = crearPago(1);
        when(metodoPagoRepository.findById(1)).thenReturn(Optional.of(metodoPago));
        when(pagoRepository.save(nuevo)).thenReturn(guardado);

        // WHEN
        PagoDTO resultado = pagoService.registrarPago(dto);

        // THEN
        assertNotNull(resultado);
        assertEquals("Tarjeta", resultado.getNombreMetodoPago());
        verify(metodoPagoRepository, times(1)).findById(1);
        verify(pagoRepository, times(1)).save(nuevo);
    }

    @Test
    void testActualizarPago() {
        // GIVEN
        Integer id = 1;
        Pago existente = crearPago(id);
        PagoDTO nuevosDatos = crearPagoDTO();
        nuevosDatos.setMonto(35000.0);
        when(pagoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(metodoPagoRepository.findById(1)).thenReturn(Optional.of(crearMetodoPago()));
        when(pagoRepository.save(existente)).thenReturn(existente);

        // WHEN
        PagoDTO resultado = pagoService.actualizarPago(id, nuevosDatos);

        // THEN
        assertEquals(Double.valueOf(35000), resultado.getMonto());
        verify(pagoRepository, times(1)).save(existente);
    }

    @Test
    void testEliminarPagoLogicamente() {
        // GIVEN
        Integer id = 1;
        Pago pago = crearPago(id);
        when(pagoRepository.findById(id)).thenReturn(Optional.of(pago));
        when(pagoRepository.save(pago)).thenReturn(pago);

        // WHEN
        String resultado = pagoService.eliminar(id);

        // THEN
        assertEquals(false, pago.getEstado());
        assertEquals("Pago deshabilitado correctamente", resultado);
        verify(pagoRepository, times(1)).save(pago);
    }

    @Test
    void testBuscarPorSocio() {
        // GIVEN
        when(pagoRepository.findByIdSocio(1)).thenReturn(List.of(crearPago(1)));

        // WHEN
        List<PagoDTO> resultado = pagoService.buscarPorSocio(1);

        // THEN
        assertEquals(1, resultado.size());
        verify(pagoRepository, times(1)).findByIdSocio(1);
    }

    @Test
    void testBuscarPorEstado() {
        // GIVEN
        when(pagoRepository.findByEstado(true)).thenReturn(List.of(crearPago(1)));

        // WHEN
        List<PagoDTO> resultado = pagoService.buscarPorEstado(true);

        // THEN
        assertEquals(1, resultado.size());
        verify(pagoRepository, times(1)).findByEstado(true);
    }

    private Pago crearPago(Integer id) {
        Pago pago = new Pago();
        pago.setId(id);
        pago.setMonto(29990.0);
        pago.setFechaPago(LocalDate.now());
        pago.setComprobante("CMP-0001");
        pago.setEstado(true);
        pago.setIdSocio(1);
        pago.setIdMembresia(1);
        pago.setMetodoPago(crearMetodoPago());
        return pago;
    }

    private PagoDTO crearPagoDTO() {
        PagoDTO dto = new PagoDTO();
        dto.setMonto(29990.0);
        dto.setFechaPago(LocalDate.now());
        dto.setComprobante("CMP-0001");
        dto.setEstado(true);
        dto.setIdSocio(1);
        dto.setIdMembresia(1);
        dto.setIdMetodoPago(1);
        return dto;
    }

    private MetodoPago crearMetodoPago() {
        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setIdMetodoPago(1);
        metodoPago.setNombre("Tarjeta");
        metodoPago.setDescripcion("Pago con tarjeta");
        metodoPago.setEstado(true);
        return metodoPago;
    }
}
