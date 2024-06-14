package com.eventHub.service;

import com.eventHub.dto.evento.CadastroEventoDto;
import com.eventHub.dto.evento.ListagemEventosDto;
import com.eventHub.model.Evento;
import com.eventHub.model.StatusEvento;
import com.eventHub.model.Usuario;
import com.eventHub.repository.EventoRepository;
import com.eventHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ListagemEventosDto> listarEventosDisponiveis(){
        List<Evento> eventos = eventoRepository.findAllByStatus(StatusEvento.ATIVO);
        List<ListagemEventosDto> dtos = eventos.stream()
                .map(ListagemEventosDto::new)
                .toList();

        return dtos;
    }

    public void cadastrarEvento(CadastroEventoDto dto){
        Usuario usuario = usuarioRepository.getReferenceById(10L);
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
