package com.eventHub.model;

import com.eventHub.dto.usuario.CadastroUsuarioDto;
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
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    private String telefone;

    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;

    public void cadastrarUsuarioParticipante(CadastroUsuarioDto dto){
        this.nome = dto.nome();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.senha = dto.senha();
        this.perfil = PerfilUsuario.PARTICIPANTE;
    }
}
