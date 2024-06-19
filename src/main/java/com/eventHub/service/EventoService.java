package com.eventHub.service;

import com.eventHub.dto.evento.CadastroEventoDto;
import com.eventHub.dto.evento.ListagemEventosDto;
import com.eventHub.model.Evento;
import com.eventHub.model.StatusEvento;
import com.eventHub.model.Usuario;
import com.eventHub.repository.EventoRepository;
import com.eventHub.repository.UsuarioRepository;
import com.eventHub.validacao.ValidacoesCadastroEventos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private List<ValidacoesCadastroEventos> validacoes;

    public List<ListagemEventosDto> listarEventosDisponiveis(){
        List<Evento> eventos = eventoRepository.findAllByStatus(StatusEvento.ATIVO);
        List<ListagemEventosDto> dtos = eventos.stream()
                .map(ListagemEventosDto::new)
                .toList();

        return dtos;
    }

    @Scheduled(cron = "0 5 * * * ?")
    private void verificaEventosConcluídos(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDate dataAtual = LocalDate.now();
        LocalTime horarioAtual = LocalTime.parse(LocalTime.now().format(formatter));

        Optional<List<Evento>> eventosConcluidos = eventoRepository.encontrarEventosJaConcluidos(horarioAtual, dataAtual);

        eventosConcluidos.ifPresent(eventos -> eventos.forEach(e -> e.setStatus(StatusEvento.CONCLUIDO)));
    }

    public void cadastrarEvento(CadastroEventoDto dto){
        validacoes.forEach(v -> v.validar(dto));

        Usuario usuario = loginService.retornarUsuarioDaSessao();
        Evento evento = new Evento(dto, usuario);
        eventoRepository.save(evento);
    }

    public List<ListagemEventosDto> listarEventosPeloNome(String nomeEvento){
        List<Evento> eventos = eventoRepository.encontrarEventosPeloNome(nomeEvento);
        List<ListagemEventosDto> dtos = eventos.stream()
                .map(ListagemEventosDto::new)
                .toList();

        return dtos;
    }
}
