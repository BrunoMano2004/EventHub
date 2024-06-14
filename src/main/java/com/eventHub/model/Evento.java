package com.eventHub.model;

import com.eventHub.dto.evento.CadastroEventoDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private String dataInicio;

    private String dataEncerramento;

    private String horarioInicio;

    private String horarioEncerramento;

    private String local;

    private int capacidade;

    private Double preco;

    @Enumerated(EnumType.STRING)
    private StatusEvento status;

    private Timestamp dataCriacao;

    private String imagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador_id")
    private Usuario usuario;

    public Evento(CadastroEventoDto dto, Usuario usuario) {
        this.nome = dto.nome();
        this.descricao = dto.descricao();
        this.horarioInicio = dto.horarioInicio();
        this.horarioEncerramento = dto.horarioEncerramento();
        this.dataInicio = dto.dataInicio();
        this.dataEncerramento = dto.dataEncerramento();
        this.local = dto.local();
        this.capacidade = dto.capacidade();
        this.preco = dto.preco();
        this.status = StatusEvento.ATIVO;
        this.imagem = dto.imagem();
        this.usuario = usuario;
    }
}
