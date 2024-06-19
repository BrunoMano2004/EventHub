package com.eventHub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CadastroEnderecoDto(

        @NotBlank
        String logradouro,

        @NotNull
        int numero,

        @NotBlank
        String bairro,

        @NotBlank
        String cidade,

        @NotBlank
        String uf,

        @NotBlank
        @Pattern(regexp = "\\d+", message = "O campo ceo deve conter apenas números")
        String cep,

        String complemento
) {
}
