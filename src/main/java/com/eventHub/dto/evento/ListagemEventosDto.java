package com.eventHub.dto.evento;

import com.eventHub.model.Evento;

public record ListagemEventosDto(String nome, String descricao, String dataInicio, String horarioInicio, String local, Double preco, String imagem) {
    public ListagemEventosDto(Evento evento){
        this(evento.getNome(), evento.getDescricao(), evento.getDataInicio(), evento.getHorarioInicio(), evento.getLocal(), evento.getPreco(), evento.getImagem());
    }
}
