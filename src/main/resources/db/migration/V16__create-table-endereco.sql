create table endereco(
    id bigint primary key auto_increment,
    logradouro varchar(100) not null,
    numero int not null,
    bairro varchar(100) not null,
    cidade varchar(100) not null,
    UF varchar(10) not null,
    CEP varchar(8) not null,
    complemento varchar(100),
    usuario_id bigint not null,
    foreign key(usuario_id) references usuarios(id)

)