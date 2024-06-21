package com.eventHub.dto.usuario;

import com.eventHub.model.Usuario;

public record ListagemUsuarioDto(Long id, String nome, String email, String telefone, String identidade, String tipoDocumento) {
    public ListagemUsuarioDto(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone(), usuario.getIdentidade(), usuario.getTipoDocumento().toString());
    }
}
