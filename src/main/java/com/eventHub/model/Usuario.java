package com.eventHub.model;

import com.eventHub.dto.endereco.AtualizacaoEnderecoDto;
import com.eventHub.dto.endereco.CadastroEnderecoDto;
import com.eventHub.dto.usuario.AtualizacaoUsuarioDto;
import com.eventHub.dto.usuario.CadastroUsuarioDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String identidade;

    @Enumerated(value = EnumType.STRING)
    private TipoDocumento tipoDocumento;

    private String telefone;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Login login;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Endereco endereco;



    public Usuario(CadastroUsuarioDto dto, CadastroEnderecoDto enderecoDto){
        this.nome = dto.nome();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.identidade = dto.identidade();
        this.tipoDocumento = TipoDocumento.valueOf(dto.tipoDocumento());
        this.endereco = new Endereco(enderecoDto, this);
    }

    public void atualizarUsuario(AtualizacaoUsuarioDto usuarioDto, AtualizacaoEnderecoDto enderecoDto){
        this.nome = usuarioDto.nome();
        this.email = usuarioDto.email();
        this.telefone = usuarioDto.telefone();
        this.endereco = endereco.atualizarEndereco(enderecoDto);
    }
}
