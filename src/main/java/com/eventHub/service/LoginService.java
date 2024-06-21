package com.eventHub.service;

import com.eventHub.model.Login;
import com.eventHub.model.Usuario;
import com.eventHub.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokeUtil jwtTokeUtil;

    public Usuario retornarUsuarioDaSessao(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Login login = loginRepository.findByEmail(user.getUsername()).get();
        return login.getUsuario();
    }

    public void redefinirSenha(String jwt, String senha){
        String email = jwtTokeUtil.extractUsername(jwt);
        Login login = loginRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email não encontrado!"));
        login.setSenha(passwordEncoder.encode(senha));
    }
}
