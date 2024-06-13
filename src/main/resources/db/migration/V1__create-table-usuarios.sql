create table usuarios(
    id bigint primary key auto_increment,
    nome varchar(100) not null,
    email varchar(100) unique not null,
    senha varchar(100) not null,
    telefone varchar(100),
    perfil enum('organizador', 'participante', 'administrador') not null,
    data_criacao timestamp default current_timestamp
);