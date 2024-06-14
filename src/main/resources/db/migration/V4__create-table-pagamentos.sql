CREATE TABLE pagamentos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    inscricao_id BIGINT NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    metodo_pagamento varchar(100) NOT NULL,
    status varchar(100) DEFAULT 'pendente',
    data_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (inscricao_id) REFERENCES inscricoes(id)
);
