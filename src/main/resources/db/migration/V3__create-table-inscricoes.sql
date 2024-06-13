CREATE TABLE inscricoes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    evento_id BIGINT NOT NULL,
    participante_id BIGINT NOT NULL,
    data_inscricao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pendente', 'confirmada', 'cancelada') DEFAULT 'pendente',
    FOREIGN KEY (evento_id) REFERENCES eventos(id),
    FOREIGN KEY (participante_id) REFERENCES usuarios(id)
);