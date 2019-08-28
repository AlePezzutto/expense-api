CREATE TABLE lancamento (
	codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL,
	data_vencto DATE NOT NULL,
	data_pagto DATE,
	valor DECIMAL(10,2) NOT NULL,
	observ VARCHAR(100),
	tipo VARCHAR(20) NOT NULL,
	codigo_cat BIGINT NOT NULL,
	pessoa_id BIGINT NOT NULL,
    FOREIGN KEY (codigo_cat) REFERENCES categoria (codigo),
    FOREIGN KEY (pessoa_id) REFERENCES pessoa (pessoa_id)	
);

