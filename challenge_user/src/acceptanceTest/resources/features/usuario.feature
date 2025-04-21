# language: pt
Funcionalidade: testar operações Create/POST, Read/GET, Update/PUT e Delete/DELETE
  Como cliente dessa API Rest, desejo cadastrar, consultar, atualizar e deletar usuário
  Dessa forma, ter respostas padrão de sucesso ou de erro
  Para saber se meu sistema foi capaz de executar um CRUD de usuário

  Contexto:
    Dado ambiente de teste ativado para Challenge_User
    Dado cadastros de Usuarios disponíveis no banco de dados
    |       nome        |         email         |     login   |     senha     |
    |  Martin Fowler    |   fowler@email.com    |   mfowler   |   fowler123   |
    |     Kent Beck     |     beck@proton.me    |     kbeck   |     beck123   |
    |  Jeff Sutherland  |     jeff@gmail.com    |   jsuther   |   suther234   |

  Cenario: Post para criar Usuário com sucesso pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "Robert Martin" e email "rm@email.com" e login "rmartin" e senha "rm123"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 201 do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "Robert Martin" e email "rm@email.com" e login "rmartin" e senha "rm123"
    E um Usuario salvo no database, com nome "Robert Martin" e email "rm@email.com" e login "rmartin" e senha "rm123"

  Cenario: Get para consultar Usuário com sucesso pelo UsuarioController
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
    E um UsuarioUpdateDtoRequest, com nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 200 do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123"
    E o Usuário no banco, possui nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123"

  Cenario: Get para consultar Usuário não encontrado pelo UsuarioController
    Dado um identificador ID de um usuário inexistente
    Quando uma requisição Get for feita no método findById do UsuarioController
    Entao receber ResponseEntity com HTTP 404 do UsuarioController

  Cenario: Delete para apagar Usuário não encontrado pelo UsuarioController
    Dado um identificador ID de um usuário inexistente
    Quando uma requisição Delete for feita no método deleteById do UsuarioController
    Entao receber ResponseEntity com HTTP 404 do UsuarioController

  Cenario: Put para atualizar Usuário não encontrado pelo UsuarioController
    Dado um identificador ID de um usuário inexistente
    E um UsuarioUpdateDtoRequest, com nome "Viktor Frankl" e email "vik@email.com" e login "vik" e senha "vik1"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 404 do UsuarioController

  Cenario: Post para criar Usuário, com erro na validação dos dados, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome " " e email "hel@email.com" e login "hel" e senha "123"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController

  Cenario: Post para criar Usuário, com erro na validação dos dados, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "Helga Weiss" e email " " e login "hel" e senha "123"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController

  Cenario: Post para criar Usuário, com erro na validação dos dados, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "  " e senha "123"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController

  Cenario: Post para criar Usuário, com erro na validação dos dados, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "hel" e senha "  "
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController

  Cenario: Put para atualizar Usuário, com erro na validação dos dados, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "  " e email "anne@email.com" e login "anne" e senha "anne1"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController
    E o Usuário no banco, possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234"

  Cenario: Put para atualizar Usuário, com erro na validação dos dados, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "Anne Frank" e email "   " e login "anne" e senha "anne1"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController
    E o Usuário no banco, possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234"

  Cenario: Put para atualizar Usuário, com erro na validação dos dados, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "   " e senha "anne1"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController
    E o Usuário no banco, possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234"

  Cenario: Put para atualizar Usuário, com erro na validação dos dados, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "anne" e senha "   "
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController
    E o Usuário no banco, possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234"


