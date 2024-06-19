package com.eventHub.model;

import com.eventHub.dto.CadastroEnderecoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;

    private int numero;

    private String bairro;

    private String cidade;

    private String uf;

    private String cep;

    private String complemento;

    @OneToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    public Endereco(CadastroEnderecoDto dto){
        this.logradouro = dto.logradouro();
        this.numero = dto.numero();
        this.bairro = dto.bairro();
        this.cidade = dto.cidade();
        this.uf = dto.uf();
        this.cep = dto.cep();
        this.complemento = dto.complemento();
    }
}
