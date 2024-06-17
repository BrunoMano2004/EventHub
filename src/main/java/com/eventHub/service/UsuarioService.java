package com.eventHub.service;

import com.eventHub.dto.login.CadastroLoginDto;
import com.eventHub.dto.usuario.CadastroUsuarioDto;
import com.eventHub.model.Login;
import com.eventHub.model.Role;
import com.eventHub.model.Usuario;
import com.eventHub.repository.LoginRepository;
import com.eventHub.repository.RoleRepository;
import com.eventHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void cadastrarUsuario(CadastroUsuarioDto usuarioDto, CadastroLoginDto loginDto) {
        Usuario usuario = new Usuario();
        usuario.cadastrarUsuario(usuarioDto);
        usuarioRepository.save(usuario);

        Login login = new Login();
        login.setUsername(loginDto.username());
        login.setSenha(passwordEncoder.encode(loginDto.senha()));
        login.setUsuario(usuario);
        login.setAtivo(true);

        Set<Role> roles = new HashSet<>();

        Role role = roleRepository.findByName(loginDto.role()).orElseThrow(() -> new RuntimeException("Role não encontrada!"));

        roles.add(role);
        login.setRoles(roles);
        loginRepository.save(login);
    }
}
