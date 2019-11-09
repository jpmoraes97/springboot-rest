CREATE TABLE categoria(

id BIGINT(20) NOT NULL AUTO_INCREMENT,
nome VARCHAR(15) NOT NULL,

PRIMARY KEY(id)

)ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO categoria(id, nome)
VALUES
(1, 'Impostos'), (2, 'Contas'), (3, 'Lazer'), (4, 'Carro'), (5, 'Casa');