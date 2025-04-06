# language: pt
Funcionalidade: testar operação GET para consultar usuário
  Como cliente dessa API Rest, desejo consultar um usuário por id
  Dessa forma, ter resposta padrão de sucesso ou de erro
  Para saber se meu sistema foi capaz de buscar o usuário no banco de dados

  Contexto:
    Dado ambiente de teste ativado para Challenge_User
    Dado cadastros de Usuarios disponíveis no banco de dados
    |       nome        |         email         |     login   |     senha     |
    |  Martin Fowler    |   fowler@email.com    |   mfowler   |   fowler123   |
    |     Kent Beck     |     beck@proton.me    |     kbeck   |     beck123   |

  Cenario: Get para consultar Usuário com sucesso pelo UserController
    Dado um identificador ID de um usuário existente, com email "fowler@email.com"
    Quando uma requisição Get for feita no método findById do UsuarioController
    Entao receber ResponseEntity com HTTP 200 do Get do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "Martin Fowler" e email "fowler@email.com" e login "mfowler"

