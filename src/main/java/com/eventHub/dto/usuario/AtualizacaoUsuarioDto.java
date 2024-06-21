package com.eventHub.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AtualizacaoUsuarioDto(

        @NotNull
        Long id,

        @NotBlank(message = "Nome não pode estar vazio!")
        String nome,

        @NotBlank(message = "Email não pode estar vazio!")
        @Email(message = "Deve estar no formato correto de email!")
        String email,

        @NotBlank(message = "Telefone não pode estar vazio!")
        @Pattern(regexp = "\\d+", message = "O campo telefone deve conter apenas números")
        String telefone) {
}
