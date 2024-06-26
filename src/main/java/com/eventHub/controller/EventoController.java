package com.eventHub.controller;

import com.eventHub.dto.evento.CadastroEventoDto;
import com.eventHub.dto.evento.ListagemEventosDto;
import com.eventHub.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/eventos")
@SessionAttributes("user")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public ModelAndView listarTodosEventosDisponiveis(){
        ModelAndView mv = new ModelAndView("index");
        List<ListagemEventosDto> eventos = eventoService.listarEventosDisponiveis();

        mv.addObject("eventos", eventos);
        return mv;
    }

    @GetMapping("/pesquisar")
    public ModelAndView listarEventosPesquisados(String nomeEvento){
        ModelAndView mv = new ModelAndView("index");
        List<ListagemEventosDto> eventos = eventoService.listarEventosPeloNome(nomeEvento);

        mv.addObject("eventos", eventos);
        return mv;
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @GetMapping("/cadastrar")
    public String cadastrarEvento(){
        return "cadastroEvento";
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @PostMapping("/cadastrar")
    @Transactional
    public String cadastrarEventoPost(@Valid CadastroEventoDto dto){
        eventoService.cadastrarEvento(dto);
        return "redirect:/eventos";
    }
}
