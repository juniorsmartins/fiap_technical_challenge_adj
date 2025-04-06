# language: pt
Funcionalidade: testar operação POST para criar usuário
  Como cliente dessa API Rest, desejo cadastrar um usuário pelo método POST
  Dessa forma, ter resposta padrão de sucesso ou de erro
  Para saber se meu sistema foi capaz de persistir o usuário no banco de dados

  Contexto:
    Dado ambiente de teste ativado para Challenge_User

  Cenario: Post para criar User com sucesso pelo UserController
    Dado um UserDtoRequest válido, com nome "Martin Fowler" e email "martin@email.com" e login "rmartin" e senha "mar123"
    Quando a requisição Post for feita no método create do UserController
    Então receber ResponseEntity com HTTP 201 do UserController
    E com UserDtoRequest no body, com nome "Martin Fowler" e email "martin@email.com" e login "rmartin" e senha "mar123"
    E um User salvo no database, com nome "Martin Fowler" e email "martin@email.com" e login "rmartin" e senha "mar123"

