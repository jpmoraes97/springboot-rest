CREATE TABLE contato (

id BIGINT(20) NOT NULL AUTO_INCREMENT,
pessoa_id BIGINT(20) NOT NULL,
nome VARCHAR(40) NOT NULL,
email VARCHAR(45),
telefone VARCHAR(20) NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(pessoa_id) REFERENCES pessoa(id)

)ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO contato (id, pessoa_id, nome, email, telefone)
VALUES
(1, 1, 'João Pedro Moraes', 'joaopedro@mail.com', '37290077'),
(2, 1, 'Mariana Moraes', 'marianamoraes@mail.com', '974565758');