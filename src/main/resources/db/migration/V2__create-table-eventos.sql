create table eventos(
    id bigint primary key auto_increment,
    organizador_id BIGINT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP NOT NULL,
    local VARCHAR(255) NOT NULL,
    capacidade INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    status varchar(100) DEFAULT 'ativo',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (organizador_id) REFERENCES usuarios(id)
)