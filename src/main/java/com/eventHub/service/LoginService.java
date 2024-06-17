package com.eventHub.service;

import com.eventHub.dto.usuario.ListagemUsuarioDto;
import com.eventHub.model.Login;
import com.eventHub.model.Usuario;
import com.eventHub.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public Usuario retornarUsuarioDaSessao(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Login login = loginRepository.findByUsername(user.getUsername()).get();
        return login.getUsuario();
    }
}
