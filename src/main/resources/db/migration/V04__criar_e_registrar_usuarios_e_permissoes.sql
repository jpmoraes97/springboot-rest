CREATE TABLE usuario(

id BIGINT(20) NOT NULL,
nome VARCHAR(50) NOT NULL,
email VARCHAR(50) NOT NULL UNIQUE,
senha VARCHAR(150) NOT NULL,
PRIMARY KEY(id)

)ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE permissao(

id BIGINT(20) NOT NULL,
descricao VARCHAR(50) NOT NULL,
PRIMARY KEY(id)

)ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE usuario_permissao(

usuario_id BIGINT(20) NOT NULL,
permissao_id BIGINT(20) NOT NULL,
PRIMARY KEY(usuario_id, permissao_id),
FOREIGN KEY(usuario_id) REFERENCES usuario(id),
FOREIGN KEY(permissao_id) REFERENCES permissao(id)

)ENGINE = InnoDB DEFAULT CHARSET = utf8;


INSERT INTO usuario(id, nome, email, senha)
VALUES
(1, 'Administrador', 'administrador@springboot.com', '$2a$10$pH/tS.qZSESZgZdBDPkG8eFiUHnuZ.QOOsjiHfBsu.YKzWsOY5.HK'),
(2, 'Secretaria', 'secretaria@sprigboot.com', '$2a$10$xQl/zMDjzFnJGbIxsLzA6e9AozIhCoefHFBvsoNdHlgU9dTihDQoC');


INSERT INTO permissao(id, descricao)
VALUES
(1, 'ROLE_CADASTRAR_CATEGORIA'),(2, 'ROLE_PESQUISAR_CATEGORIA'), (3, 'ROLE_REMOVER_CATEGORIA'),
(4, 'ROLE_CADASTRAR_PESSOA'), (5, 'ROLE_PESQUISAR_PESSOA'), (6, 'ROLE_REMOVER_PESSOA'),
(7, 'ROLE_CADASTRAR_LANCAMENTO'), (8, 'ROLE_PESQUISAR_LANCAMENTO'), (9, 'ROLE_REMOVER_LANCAMENTO');

INSERT INTO usuario_permissao(usuario_id, permissao_id)
VALUES
-- administrador
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),
-- secretaria
(2,2),(2,4),(2,6);




