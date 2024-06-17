create table logins(
    id bigint auto_increment primary key,
    username varchar(100) unique not null,
    senha varchar(100) not null,
    ativo boolean not null,
    id_usuario bigint not null,
    foreign key (id_usuario) references usuarios(id)
)