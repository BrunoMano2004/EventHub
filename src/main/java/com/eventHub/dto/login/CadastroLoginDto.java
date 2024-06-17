package com.eventHub.dto.login;

import jakarta.validation.constraints.NotBlank;

public record CadastroLoginDto(

        @NotBlank
        String username,

        @NotBlank
        String senha,

        @NotBlank
        String role
) {
}
