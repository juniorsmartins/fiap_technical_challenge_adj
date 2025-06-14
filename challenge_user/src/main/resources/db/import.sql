INSERT INTO enderecos(endereco_id, cep, logradouro, numero) VALUES('4f50648e-639d-423a-9a46-f4a8d1e96b07', '69905-169', 'Travessa Nilo Bezerra', '500');
INSERT INTO enderecos(endereco_id, cep, logradouro, numero) VALUES('343cbd6d-b20e-4836-80bb-4a07e47d38ba', '69915-636', 'Alameda Tarauacá', '870');

INSERT INTO clientes(usuario_id, nome, email, login, senha, numero_cartao_fidelidade, data_hora_criacao, data_hora_edicao, endereco_id) VALUES('a546ef31-d9f4-4ff7-9665-4baed324920b', 'Carl Friedrich Gauss', 'gauss@email.com', 'gauss', 'gauss123', '4321-1234-001', TO_TIMESTAMP('2023-10-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-10-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), '4f50648e-639d-423a-9a46-f4a8d1e96b07');
INSERT INTO clientes(usuario_id, nome, email, login, senha, numero_cartao_fidelidade, data_hora_criacao, data_hora_edicao, endereco_id) VALUES('a90902fa-7cce-4c17-87fd-5cd9c70c9d5a', 'Isaac Newton', 'newton@email.com', 'newton', 'newton123', '4321-1234-002', TO_TIMESTAMP('2023-10-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-12-04 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), '343cbd6d-b20e-4836-80bb-4a07e47d38ba');
INSERT INTO clientes(usuario_id, nome, email, login, senha, numero_cartao_fidelidade, data_hora_criacao, data_hora_edicao, endereco_id) VALUES('6d648275-37d9-4fd3-800f-025a2262ef4d', 'Leonhard Euler', 'euler@email.com', 'euler', 'euler123', '4321-1234-003', TO_TIMESTAMP('2023-10-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2022-11-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), null);
INSERT INTO clientes(usuario_id, nome, email, login, senha, numero_cartao_fidelidade, data_hora_criacao, data_hora_edicao, endereco_id) VALUES('86522917-f507-459a-8ef9-93015089a5b2', 'Brian W. Kernighan', 'kernighan@email.com', 'brian', 'brian123', '7678-1111-123', TO_TIMESTAMP('2020-11-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2022-12-08 12:05:00', 'YYYY-MM-DD HH24:MI:SS'), null);

INSERT INTO enderecos(endereco_id, cep, logradouro, numero) VALUES('eac614d5-c70b-4b36-b4c8-7560f6f0eef9', '69905-169', 'Rua Antônio Francisco das Chagas', '100');
INSERT INTO enderecos(endereco_id, cep, logradouro, numero) VALUES('e2619c26-ae4e-4a2b-bd20-ae0ed7c4c62d', '69800-610', 'Avenida Central', '870');

INSERT INTO proprietarios(usuario_id, nome, email, login, senha, descricao, data_hora_criacao, data_hora_edicao, endereco_id) VALUES('eb957f38-90c4-4ef2-850c-229fb1658fcd', 'Linus Pauling', 'linus@email.com', 'linus', 'linus123', 'Químico', TO_TIMESTAMP('2023-10-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-11-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'eac614d5-c70b-4b36-b4c8-7560f6f0eef9');
INSERT INTO proprietarios(usuario_id, nome, email, login, senha, descricao, data_hora_criacao, data_hora_edicao, endereco_id) VALUES('bc11e003-219d-4884-88e9-e2a0b43d42c7', 'Fritz Haber', 'fritz@email.com', 'fritz', 'fritz123', 'Contador', TO_TIMESTAMP('2023-10-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2023-10-08 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'e2619c26-ae4e-4a2b-bd20-ae0ed7c4c62d');
INSERT INTO proprietarios(usuario_id, nome, email, login, senha, descricao, data_hora_criacao, data_hora_edicao, endereco_id) VALUES('051f5dc8-74fe-4d2c-81e2-ddea7c515532', 'Marie Curie', 'curie@email.com', 'curie', 'curie123', 'Advogado', TO_TIMESTAMP('2023-10-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-12-09 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), null);
INSERT INTO proprietarios(usuario_id, nome, email, login, senha, descricao, data_hora_criacao, data_hora_edicao, endereco_id) VALUES('b69e9f12-e489-4751-9b24-a936c4f3c4d2', 'Andrew S. Tanenbaum', 'tanenbaum@email.com', 'tanenbaum', 'tanenbaum12', 'Porteiro', TO_TIMESTAMP('2019-01-21 12:02:20', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-05-05 12:50:50', 'YYYY-MM-DD HH24:MI:SS'), null);

INSERT INTO enderecos(endereco_id, cep, logradouro, numero) VALUES('ee4d3283-028e-4883-9d43-169240b56758', '70680-777', 'Rua Manoel das Pitangas', '666');
INSERT INTO enderecos(endereco_id, cep, logradouro, numero) VALUES('5dc8d58a-7cf9-422b-8167-985da17fd563', '80999-555', 'Avenida do Porto', '555');
INSERT INTO enderecos(endereco_id, cep, logradouro, numero) VALUES('5f887464-fb36-4556-a78e-b567a85e62be', '20987-222', 'Avenida do Porto', '555');

INSERT INTO restaurantes(restaurante_id, nome, endereco_id) VALUES('63fd1b44-4fd9-4d67-95d9-9a4607409c79', 'Lasai', 'ee4d3283-028e-4883-9d43-169240b56758');
INSERT INTO restaurantes(restaurante_id, nome, endereco_id) VALUES('b173aa00-d852-4ff2-86aa-ce657136a44a', 'Oteque', '5dc8d58a-7cf9-422b-8167-985da17fd563');
INSERT INTO restaurantes(restaurante_id, nome, endereco_id) VALUES('6bfe2290-0720-4c58-89d7-902698778e59', 'Casa de Taipa', '5f887464-fb36-4556-a78e-b567a85e62be');

