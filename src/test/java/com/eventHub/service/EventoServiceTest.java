package com.eventHub.service;

import com.eventHub.dto.evento.CadastroEventoDto;
import com.eventHub.dto.evento.ListagemEventosDto;
import com.eventHub.dto.usuario.CadastroUsuarioDto;
import com.eventHub.model.*;
import com.eventHub.repository.EventoRepository;
import com.eventHub.repository.UsuarioRepository;
import com.eventHub.validacao.ValidacoesCadastroEventos;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @InjectMocks
    private EventoService eventoService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private List<ValidacoesCadastroEventos> validacoes = new ArrayList<>();

    @Test
    void deveriaRetornarListaComEventosDisponiveis(){
        Evento evento = mock(Evento.class, RETURNS_DEEP_STUBS);
        Evento evento1 = mock(Evento.class, RETURNS_DEEP_STUBS);

        List<Evento> eventos = Arrays.asList(evento, evento1);

        when(eventoRepository.findAllByStatus(StatusEvento.ATIVO)).thenReturn(eventos);

        ListagemEventosDto eventoDto1 = new ListagemEventosDto(evento);
        ListagemEventosDto eventoDto2 = new ListagemEventosDto(evento1);

        List<ListagemEventosDto> eventosDto = Arrays.asList(eventoDto1, eventoDto2);

        assertEquals(eventosDto, eventoService.listarEventosDisponiveis());
    }
}