alter table usuarios add column identidade varchar(50) not null unique;
alter table usuarios add column tipo_documento varchar(4) not null