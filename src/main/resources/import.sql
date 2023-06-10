-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-1');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-2');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-3');

insert into pessoa (id, nome, cpf, sexo) values(1, 'teste', '111.111.111-11', 2);

insert into estado (nome, sigla) values('Tocantins', 'TO');
insert into estado (nome, sigla) values('Goiás', 'GO');
insert into estado (nome, sigla) values('São Paulo', 'SP');
insert into estado (nome, sigla) values('Rio de Janeiro', 'RJ');
insert into estado (nome, sigla) values('Pará', 'PA');

insert into cidade (nome, id_estado) values('Palmas', 1);
insert into cidade (nome, id_estado) values('Paraiso do Tocantins', 1);

insert into telefone (codigoArea, numero) values('63', '11111-1111');
insert into telefone (codigoArea, numero) values('63', '22222-2222');
insert into telefone (codigoArea, numero) values('62', '33333-3333');
insert into telefone (codigoArea, numero) values('11', '44444-4444');

insert into usuario (login, senha, id_pessoa) values('teste', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 1);

insert into perfis (id_usuario, perfil) values (1, 'Admin');
insert into perfis (id_usuario, perfil) values (1, 'User');

-- insert into brinquedo (id,nome, preco, idade, marca, estoque, descricao) values (1, 'carro', 10, '6 anos', 'tectoy', 20, 'carrinho para criança');