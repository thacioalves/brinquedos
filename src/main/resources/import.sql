-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-1');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-2');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-3');

--INSERT INTO estado (id, nome, sigla) VALUES
--(1, 'São Paulo', 'SP'),
--(2, 'Rio de Janeiro', 'RJ'),
--(3, 'Minas Gerais', 'MG');

--INSERT INTO cidade (nome, id_estado) VALUES
--('São Paulo', 1),
--('Rio de Janeiro', 2),
--('Belo Horizonte', 3);

--INSERT INTO endereco (id, logradouro, bairro, numero, complemento, cep, id_cidade) VALUES
--(4, 'Rua D', 'Bairro A', '123', 'Complemento A', '12345-678', 1),
--(6, 'Rua F', 'Bairro C', '354', 'Complemento C', '12334-432', 2);


INSERT INTO usuario(nome, cpf, email, senha) VALUES('Thacio', '12312312312', 'thacio@gmail.com', 'senha')

INSERT INTO perfis (id_usuario, perfis) VALUES(1, 'Admin');



