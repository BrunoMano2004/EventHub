package com.eventHub.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;

    private MetodoPagamento metodoPagamento;

    private Timestamp dataPagamento;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inscricao_id")
    private Inscricao inscricao;

}
