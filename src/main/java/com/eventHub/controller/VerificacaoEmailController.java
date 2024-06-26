package com.eventHub.controller;

import com.eventHub.exception.ValidacaoEmailException;
import com.eventHub.service.ValidacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VerificacaoEmailController {

    @Autowired
    private ValidacaoService validacaoService;

    @GetMapping("/validar/{jwt}")
    @Transactional
    public String validarEmail(@PathVariable String jwt){
        try {
            validacaoService.validarEmail(jwt);
            return "redirect:/login";
        } catch (ValidacaoEmailException e){
            return "falhaVerificacaoEmail";
        }
    }
}
