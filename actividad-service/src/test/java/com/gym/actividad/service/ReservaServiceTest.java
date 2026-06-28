package com.gym.actividad.service;

import com.gym.actividad.dto.ReservaDTO;

import com.gym.actividad.model.Reserva;
import com.gym.actividad.repository.ClaseRepository;
import com.gym.actividad.repository.ReservaRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.reactive.function.client.WebClient;


import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClaseRepository claseRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;


    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ReservaService reservaService;

   
    @Test
    void crearReserva_LanzarErrorCuandoClaseNoExiste() {
     
        ReservaDTO dto = new ReservaDTO();
        dto.setFechaReserva(LocalDate.of(2026, 6, 28));
        dto.setEstado("ACTIVA");
        dto.setClaseId(99);
        dto.setSocioId(1);

        when(claseRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaService.crearReserva(dto);
        });

        assertEquals("Clase no encontrada", exception.getMessage());

        verify(claseRepository, times(1)).findById(99);
        verify(reservaRepository, never()).save(any(Reserva.class));
        verify(webClientBuilder, never()).build();
    }
}