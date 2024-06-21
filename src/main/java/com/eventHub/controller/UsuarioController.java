package com.eventHub.controller;

import com.eventHub.dto.endereco.AtualizacaoEnderecoDto;
import com.eventHub.dto.endereco.CadastroEnderecoDto;
import com.eventHub.dto.endereco.ListagemEnderecoDto;
import com.eventHub.dto.login.CadastroLoginDto;
import com.eventHub.dto.usuario.AtualizacaoUsuarioDto;
import com.eventHub.dto.usuario.CadastroUsuarioDto;
import com.eventHub.dto.usuario.ListagemUsuarioDto;
import com.eventHub.model.Usuario;
import com.eventHub.service.LoginService;
import com.eventHub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/cadastro")
    public String cadastroUsuario(){
        return "cadastroUsuario";
    }

    @PostMapping("/cadastro")
    public String cadastroUsuarioPost(@Valid CadastroUsuarioDto usuarioDto, @Valid CadastroLoginDto loginDto, @Valid CadastroEnderecoDto enderecoDto){
        usuarioService.cadastrarUsuario(usuarioDto, loginDto, enderecoDto);
        return "avisoValidacaoEmail";
    }

    @PostMapping("/dashboard")
    @Transactional
    public String atualizarUsuarioPut(@Valid AtualizacaoUsuarioDto usuarioDto, @Valid AtualizacaoEnderecoDto enderecoDto){
        if (usuarioService.atualizarDadosUsuario(usuarioDto, enderecoDto)){
            return "redirect:/usuarios/dashboard";
        } else {
            return "avisoValidacaoEmail";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Usuario usuario = loginService.retornarUsuarioDaSessao();
        ListagemUsuarioDto dto = new ListagemUsuarioDto(usuario);
        ListagemEnderecoDto enderecoDto = new ListagemEnderecoDto(usuario.getEndereco());
        model.addAttribute("usuario", dto);
        model.addAttribute("endereco", enderecoDto);
        return "dashboard";
    }
}
