package com.eventHub.service;

import com.eventHub.dto.usuario.CadastroUsuarioDto;
import com.eventHub.model.Usuario;
import com.eventHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void cadastrarUsuario(CadastroUsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.cadastrarUsuario(dto);
        usuarioRepository.save(usuario);
    }
}
