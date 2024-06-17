package com.eventHub.dto.usuario;

import com.eventHub.model.Usuario;

public record ListagemUsuarioDto(String nome, String email, String telefone) {
    public ListagemUsuarioDto(Usuario usuario){
        this(usuario.getNome(), usuario.getEmail(), usuario.getTelefone());
    }
}
