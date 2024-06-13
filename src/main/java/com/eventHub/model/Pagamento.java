package com.eventHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
