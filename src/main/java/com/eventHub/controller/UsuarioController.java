package com.eventHub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping("/cadastro/participante")
    public String cadastroUsuarioParticipante(){
        return "cadastroParticipante";
    }
}
