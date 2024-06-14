package com.eventHub.controller;

import com.eventHub.dto.usuario.CadastroUsuarioDto;
import com.eventHub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public String cadastroUsuario(){
        return "cadastroParticipante";
    }

    @PostMapping("/cadastro")
    @Transactional
    public String cadastroUsuarioPost(@Valid CadastroUsuarioDto dto){
        usuarioService.cadastrarUsuario(dto);
        return "redirect:/usuarios/cadastro";
    }
}
