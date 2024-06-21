package com.eventHub.controller;

import com.eventHub.exception.ValidacaoEmailException;
import com.eventHub.service.LoginService;
import com.eventHub.service.UsuarioService;
import com.eventHub.service.ValidacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ValidacaoService validacaoService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/recuperarSenha")
    public String recuperarSenhaGet(){
        return "recuperarSenha";
    }

    @PostMapping("/recuperarSenha")
    public String recuperarSenhaPost(String email){
        usuarioService.sendJwtEmail(email, "http://localhost:8080/redefinirSenha/");
        return "avisoValidacaoEmail";
    }

    @GetMapping("/redefinirSenha/{jwt}")
    public String validarSenhaGet(@PathVariable String jwt){
        try {
            validacaoService.validarEmail(jwt);
            return "redefinicaoSenha";
        } catch (ValidacaoEmailException e){
            return "redirect:/login";
        }
    }

    @PostMapping("redefinirSenha/{jwt}")
    @Transactional
    public String redefinirSenhaPost(@PathVariable String jwt, String senha){
        loginService.redefinirSenha(jwt, senha);
        return "redirect:/login";
    }
}
