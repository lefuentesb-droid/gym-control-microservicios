package com.gym.socio.service;

import com.gym.socio.dto.SocioDTO;
import com.gym.socio.model.Socio;
import com.gym.socio.repository.SocioRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SocioServiceTest {

    @Mock
    private SocioRepository socioRepository;

    @InjectMocks
    private SocioService socioService;

    @Test
    void buscarPorId_RetornarSocioCuandoExiste() {
        
        Socio socio = new Socio();
        socio.setId(1);
        socio.setRut("12345678-9");
        socio.setNombre("Leo");
        socio.setApellido("Fuentes");
        socio.setCorreo("leo@gmail.com");
        socio.setTelefono("912345678");
        socio.setDireccion("Maipu");
        socio.setFechaNacimiento(LocalDate.of(2000, 5, 10));
        socio.setEstado(true);
        socio.setFechaRegistro(LocalDate.now());
        when(socioRepository.findById(1)).thenReturn(Optional.of(socio));
        SocioDTO resultado = socioService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Leo", resultado.getNombre());
        assertEquals("Fuentes", resultado.getApellido());
        assertEquals("leo@gmail.com", resultado.getCorreo());
        assertTrue(resultado.getEstado());
        verify(socioRepository, times(1)).findById(1);
    }

    @Test
    void buscarPorId_LanzarErrorCuandoNoExiste() {
        when(socioRepository.findById(99)).thenReturn(Optional.empty()); 
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            socioService.buscarPorId(99);
        });
        assertEquals("¡Usuario no encontrado!", exception.getMessage());
        verify(socioRepository, times(1)).findById(99);
    }
}