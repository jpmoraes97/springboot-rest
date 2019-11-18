CREATE TABLE pessoa(

id BIGINT(20) NOT NULL AUTO_INCREMENT,
nome VARCHAR(40) NOT NULL,
cpf VARCHAR(11) NOT NULL,
ativo TINYINT(1) NOT NULL,

logradouro VARCHAR(60) NOT NULL,
numero VARCHAR(6) NOT NULL,
complemento VARCHAR(40),
bairro VARCHAR(30) NOT NULL,
cidade VARCHAR(25) NOT NULL,
estado VARCHAR(20) NOT NULL,
cep VARCHAR(8) NOT NULL,

PRIMARY KEY(id)

)ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO pessoa(id, nome, cpf, ativo, logradouro, numero, complemento, bairro, cidade, estado, cep)
VALUES
(1, 'Jailson Monstro', '42056798732', 1, 'Avenida Prestes Maia', '200', 'Bloco B Apto 02', 'Jardim do Lago', 'Campinas', 'Sao Paulo', '13060345'),
(2, 'Marcos Rocha', '43054798732', 1, 'Avenida Prestes Maia', '200', 'Bloco B Apto 02', 'Jardim do Lago', 'Campinas', 'Sao Paulo', '13060345'),
(3, 'Felipe Melo', '43056698732', 0, 'Avenida Prestes Maia', '200', 'Bloco B Apto 02', 'Jardim do Lago', 'Campinas', 'Sao Paulo', '13060345'),
(4, 'Bruno Henrique', '47656798732', 0, 'Avenida Prestes Maia', '200', 'Bloco B Apto 02', 'Jardim do Lago', 'Campinas', 'Sao Paulo', '13060345'),
(5, 'Gustavo Scarpa', '33256798732', 1, 'Avenida Prestes Maia', '200', 'Bloco B Apto 02', 'Jardim do Lago', 'Campinas', 'Sao Paulo', '13060345'),
(6, 'Willian Gomes', '23036798732', 0, 'Avenida Prestes Maia', '200', 'Bloco B Apto 02', 'Jardim do Lago', 'Campinas', 'Sao Paulo', '13060345');
