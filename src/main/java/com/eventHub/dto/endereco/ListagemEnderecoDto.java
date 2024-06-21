package com.eventHub.dto.endereco;

import com.eventHub.model.Endereco;

public record ListagemEnderecoDto(String logradouro, int numero, String bairro, String cidade, String uf, String cep, String complemento) {
    public ListagemEnderecoDto(Endereco endereco){
        this(endereco.getLogradouro(), endereco.getNumero(), endereco.getBairro(), endereco.getCidade(), endereco.getUf(), endereco.getCep(), endereco.getComplemento());
    }
}
