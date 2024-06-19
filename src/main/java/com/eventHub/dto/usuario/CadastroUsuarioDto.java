package com.eventHub.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.NumberFormat;

public record CadastroUsuarioDto(

        @NotBlank(message = "Nome não pode estar vazio!")
        String nome,

        @NotBlank(message = "Identidade não pode estar vazia!")
        @Pattern(regexp = "\\d+", message = "O campo identidade deve conter apenas números")
        String identidade,

        @NotBlank
        String tipoDocumento,

        @NotBlank(message = "Email não pode estar vazio!")
        @Email(message = "Deve estar no formato correto de email!")
        String email,

        @NotBlank(message = "Telefone não pode estar vazio!")
        @Pattern(regexp = "\\d+", message = "O campo telefone deve conter apenas números")
        String telefone ){
}
