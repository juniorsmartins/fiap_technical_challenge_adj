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
    |  Jeff Sutherland  |     jeff@gmail.com    |   jsuther   |   suther234   |

  Cenario: Get para consultar Usuário com sucesso pelo UserController
    Dado um identificador ID de um usuário existente, com email "fowler@email.com"
    Quando uma requisição Get for feita no método findById do UsuarioController
    Entao receber ResponseEntity com HTTP 200 do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "Martin Fowler" e email "fowler@email.com" e login "mfowler" e senha "fowler123"

  Cenario: Delete para apagar Usuário com sucesso pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "beck@proton.me"
    Quando uma requisição Delete for feita no método deleteById do UsuarioController
    Entao receber ResponseEntity com HTTP 204 do UsuarioController
    E o Usuário foi apagado do banco de dados pelo UsuarioController

  Cenario: Put para atualizar Usuário com sucesso pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest válido, com nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 200 do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123"
    E o Usuário foi atualizado no banco, com nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123"


