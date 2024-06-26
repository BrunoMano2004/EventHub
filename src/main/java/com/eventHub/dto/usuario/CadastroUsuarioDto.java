package com.eventHub.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CadastroUsuarioDto(

        @NotBlank(message = "Nome não pode estar vazio!")
        String nome,

        @NotBlank(message = "Identidade não pode estar vazia")
        @Pattern(regexp = "\\d+", message = "O campo identidade deve conter apenas números")
        @Size(min = 11, max = 14, message = "A identidade deve ter 11 dígitos para CPF ou 14 dígitos para CNPJ")
        String identidade,

        @NotBlank
        String tipoDocumento,

        @NotBlank(message = "Email não pode estar vazio")
        @Email(message = "Deve estar no formato correto de email")
        String email,

        @NotBlank(message = "Telefone não pode estar vazio")
        @Pattern(regexp = "\\d+", message = "O campo telefone deve conter apenas números")
        @Size(min = 10, max = 11, message = "Telefone deve conter entre 10 e 11 dígitos")
        String telefone ){
}
