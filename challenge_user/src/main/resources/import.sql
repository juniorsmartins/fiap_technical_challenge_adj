INSERT INTO enderecos(endereco_id, cep, logradouro, numero) VALUES('4f50648e-639d-423a-9a46-f4a8d1e96b07', '69905-169', 'Travessa Nilo Bezerra', '500');
INSERT INTO enderecos(endereco_id, cep, logradouro, numero) VALUES('343cbd6d-b20e-4836-80bb-4a07e47d38ba', '69915-636', 'Alameda Tarauacá', '870');

INSERT INTO usuarios(usuario_id, nome, email, login, senha, data_hora_criacao, data_hora_edicao, endereco_id) VALUES('a546ef31-d9f4-4ff7-9665-4baed324920b', 'Carl Friedrich Gauss', 'gauss@email.com', 'gauss', 'gauss123', TO_TIMESTAMP('2023-10-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-10-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), '4f50648e-639d-423a-9a46-f4a8d1e96b07');
INSERT INTO usuarios(usuario_id, nome, email, login, senha, data_hora_criacao, data_hora_edicao, endereco_id) VALUES('a90902fa-7cce-4c17-87fd-5cd9c70c9d5a', 'Isaac Newton', 'newton@email.com', 'newton', 'newton123', TO_TIMESTAMP('2023-10-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-12-04 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), '343cbd6d-b20e-4836-80bb-4a07e47d38ba');
INSERT INTO usuarios(usuario_id, nome, email, login, senha, data_hora_criacao, data_hora_edicao) VALUES('6d648275-37d9-4fd3-800f-025a2262ef4d', 'Leonhard Euler', 'euler@email.com', 'euler', 'euler123', TO_TIMESTAMP('2023-10-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2024-11-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'));

