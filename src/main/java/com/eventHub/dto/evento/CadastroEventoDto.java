package com.eventHub.dto.evento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CadastroEventoDto(

        @NotBlank
        String nome,

        @NotBlank
        String descricao,

        @NotBlank
        String dataInicio,

        @NotBlank
        String dataEncerramento,

        @NotBlank
        String horarioEncerramento,

        @NotBlank
        String horarioInicio,

        @NotBlank
        String local,

        @NotNull
        int capacidade,

        @NotNull
        Double preco,

        String imagem) {
}
