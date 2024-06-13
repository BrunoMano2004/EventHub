package com.eventHub.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private Timestamp data_inicio;

    private Timestamp data_fim;

    private String local;

    private int capacidade;

    private Double preco;

    private StatusEvento status;

    private Timestamp dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
