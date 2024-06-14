package com.eventHub.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroUsuarioDto(

        @NotBlank(message = "Nome não pode estar vazio!")
        String nome,

        @NotBlank(message = "Email não pode estar vazio!")
        @Email(message = "Deve estar no formato correto de email!")
        String email,

        @NotBlank(message = "Telefone não pode estar vazio!")
        String telefone,

        @NotBlank(message = "Senha não pode estar vazia!")
        String senha,

        @NotBlank(message = "Perfil não pode estar vazio!")
        String perfil) {
}
