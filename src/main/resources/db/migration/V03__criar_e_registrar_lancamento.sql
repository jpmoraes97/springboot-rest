CREATE TABLE lancamento (
	
id BIGINT(20) NOT NULL AUTO_INCREMENT,
descricao VARCHAR(70) NOT NULL,
data_vencimento DATE NOT NULL,
data_pagamento DATE,
valor NUMERIC(12,2) NOT NULL,
observacao VARCHAR(70),
tipo VARCHAR(7) NOT NULL,
pessoa_id BIGINT(20) NOT NULL,
categoria_id BIGINT(20) NOT NULL,

PRIMARY KEY(id),
FOREIGN KEY(pessoa_id) REFERENCES pessoa(id),
FOREIGN KEY(categoria_id) REFERENCES categoria(id) 	

)ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO lancamento(id, descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa_id, categoria_id)
VALUES
(1, 'descricao teste teste teste', '2019-11-10', '2019-11-11', 556, 'observacao teste teste', 'RECEITA', 1, 1),
(2, 'descricao teste teste teste', '2019-11-10', '2019-11-11', 767, 'observacao teste teste', 'DESPESA', 2, 2),
(3, 'descricao teste teste teste', '2019-11-10', '2019-11-11', 883, 'observacao teste teste', 'RECEITA', 3, 3),
(4, 'descricao teste teste teste', '2019-11-10', '2019-11-11', 500, 'observacao teste teste', 'DESPESA', 1, 2),
(5, 'descricao teste teste teste', '2019-11-10', '2019-11-11', 500, 'observacao teste teste', 'RECEITA', 2, 3),
(6, 'descricao teste teste teste', '2019-11-10', '2019-11-11', 500, 'observacao teste teste', 'DESPESA', 3, 3),
(7, 'descricao teste teste teste', '2019-11-10', '2019-11-11', 500, 'observacao teste teste', 'RECEITA', 1, 2),
(8, 'descricao teste teste teste', '2019-11-10', '2019-11-11', 654, 'observacao teste teste', 'DESPESA', 2, 1),
(9, 'descricao teste teste teste', '2019-11-10', '2019-11-11', 323, 'observacao teste teste', 'DESPESA', 2, 4),
(10, 'descricao teste teste teste', '2019-11-10', '2019-11-11', 1507, 'observacao teste teste', 'RECEITA', 3, 4);