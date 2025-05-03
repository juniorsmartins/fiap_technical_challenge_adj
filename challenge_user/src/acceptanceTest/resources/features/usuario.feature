# language: pt
Funcionalidade: testar operações Create/POST, Read/GET, Update/PUT e Delete/DELETE
  Como cliente dessa API Rest, desejo cadastrar, consultar, atualizar e deletar usuário
  Dessa forma, ter respostas padrão de sucesso ou de erro
  Para saber se meu sistema foi capaz de executar um CRUD de usuário

  Contexto:
    Dado ambiente de teste ativado para Challenge_User
    Dado cadastros de Usuarios disponíveis no banco de dados
    |       nome        |         email         |     login   |     senha     |     tipo     |      cep     |  logradouro  |   numero   |
    |  Martin Fowler    |   fowler@email.com    |   mfowler   |   fowler123   |    CLIENTE   |      null    |      null    |    null    |
    |     Kent Beck     |     beck@proton.me    |     kbeck   |     beck123   | PROPRIETARIO |      null    |      null    |    null    |
    |  Jeff Sutherland  |     jeff@gmail.com    |   jsuther   |   suther234   |    CLIENTE   |      null    |      null    |    null    |
    |    James Clear    |    james@gmail.com    |    james    |    james12    |    CLIENTE   |   78098-179  |     Rua L    |     300    |

  Cenario: Post para criar Usuário Cliente, com sucesso, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "Robert Martin" e email "rm@email.com" e login "rmartin" e senha "rm123" e tipo "CLIENTE"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 201 do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "Robert Martin" e email "rm@email.com" e login "rmartin" e senha "rm123" e tipo "CLIENTE"
    E o Usuário cadastrado no banco de dados possui nome "Robert Martin" e email "rm@email.com" e login "rmartin" e senha "rm123" e tipo "CLIENTE"

  Cenario: Post para criar Usuário Proprietário, com sucesso, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e tipo "PROPRIETARIO"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 201 do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e tipo "PROPRIETARIO"
    E o Usuário cadastrado no banco de dados possui nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e tipo "PROPRIETARIO"

  Cenario: Post para criar Usuário, com erro por nome vazio, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "   " e email "hel@email.com" e login "helga" e senha "12345" e tipo "CLIENTE"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController

  Cenario: Post para criar Usuário, com erro por nome maior, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "nome grande nome grande nome grande nome grande nome grande nome grande nome grande" e email "hel@email.com" e login "helga" e senha "12345" e tipo "CLIENTE"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController

  Cenario: Post para criar Usuário, com erro por email vazio, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "Helga Weiss" e email " " e login "helga" e senha "12345" e tipo "CLIENTE"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController

  Cenario: Post para criar Usuário, com erro por email incorreto, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "Helga Weiss" e email "helga.com" e login "helga" e senha "12345" e tipo "CLIENTE"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController

  Cenario: Post para criar Usuário, com erro por login vazio, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "  " e senha "12345" e tipo "CLIENTE"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController

  Cenario: Post para criar Usuário, com erro por login maior, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "helgaweiss.helgaweiss.helgaweiss.helgaweiss.helgaweiss" e senha "12345" e tipo "CLIENTE"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController

  Cenario: Post para criar Usuário, com erro por senha vazia, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "helga" e senha "  " e tipo "CLIENTE"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController

  Cenario: Post para criar Usuário, com erro por senha maior, pelo UsuarioController
    Dado um UsuarioDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "helga" e senha "anne01234567890123456789012345678901234567890123456789" e tipo "CLIENTE"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController

  Cenario: Post para criar Usuário e Endereço, com sucesso, pelo UsuarioController
    Dado um UsuarioDtoRequest e EnderecoDtoRequest, com nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e tipo "CLIENTE" e com cep "23520-123" e logradouro "Rua Hermes Lima" e número "700"
    Quando a requisição Post for feita no método create do UsuarioController
    Entao receber ResponseEntity com HTTP 201 do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e tipo "CLIENTE"
    E com EnderecoDtoResponse no body, com id e cep "23520-123" e logradouro "Rua Hermes Lima" e número "700"
    E o Usuário cadastrado no banco de dados possui nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e tipo "CLIENTE"
    E um Endereço salvo no database, com cep "23520-123" e logradouro "Rua Hermes Lima" e número "700"


  Cenario: Get para consultar Usuário, com sucesso, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "fowler@email.com"
    Quando uma requisição Get for feita no método findById do UsuarioController
    Entao receber ResponseEntity com HTTP 200 do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "Martin Fowler" e email "fowler@email.com" e login "mfowler" e senha "fowler123" e tipo "CLIENTE"

  Cenario: Get para consultar Usuário não encontrado pelo UsuarioController
    Dado um identificador ID de um usuário inexistente
    Quando uma requisição Get for feita no método findById do UsuarioController
    Entao receber ResponseEntity com HTTP 404 do UsuarioController


  Cenario: Delete para apagar Usuário, com sucesso, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "beck@proton.me"
    Quando uma requisição Delete for feita no método deleteById do UsuarioController
    Entao receber ResponseEntity com HTTP 204 do UsuarioController
    E o Usuário foi apagado do banco de dados pelo UsuarioController

  Cenario: Delete para apagar Usuário não encontrado pelo UsuarioController
    Dado um identificador ID de um usuário inexistente
    Quando uma requisição Delete for feita no método deleteById do UsuarioController
    Entao receber ResponseEntity com HTTP 404 do UsuarioController


  Cenario: Put para atualizar Usuário, com sucesso, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e tipo "CLIENTE"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 200 do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e tipo "CLIENTE"
    E o Usuário cadastrado no banco de dados possui nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e tipo "CLIENTE"

  Cenario: Put para atualizar Usuário não encontrado pelo UsuarioController
    Dado um identificador ID de um usuário inexistente
    E um UsuarioUpdateDtoRequest, com nome "Viktor Frankl" e email "vik@email.com" e login "viktor" e senha "vikt1" e tipo "CLIENTE"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 404 do UsuarioController

  Cenario: Put para atualizar Usuário, com erro por nome vazio, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "  " e email "anne@email.com" e login "annee" e senha "anne1" e tipo "CLIENTE"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController
    E o Usuário no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e tipo "CLIENTE"

  Cenario: Put para atualizar Usuário, com erro por nome maior, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "Anne Frank abcdefghijl abcdefghijl abcdefghijl abcdefghijl abcdefghijl abcdefghijl abcdefghijl" e email "anne@email.com" e login "annee" e senha "anne1" e tipo "CLIENTE"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController
    E o Usuário no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e tipo "CLIENTE"

  Cenario: Put para atualizar Usuário, com erro por email vazio, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "Anne Frank" e email "   " e login "anne" e senha "anne1" e tipo "CLIENTE"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController
    E o Usuário no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e tipo "CLIENTE"

  Cenario: Put para atualizar Usuário, com erro por email incorreto, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "Anne Frank" e email "anne.com" e login "anne" e senha "anne1" e tipo "CLIENTE"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController
    E o Usuário no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e tipo "CLIENTE"

  Cenario: Put para atualizar Usuário, com erro por login vazio, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "   " e senha "anne1" e tipo "CLIENTE"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController
    E o Usuário no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e tipo "CLIENTE"

  Cenario: Put para atualizar Usuário, com erro por login maior, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "annefrank1annefrank1annefrank1annefrank1annefrank12" e senha "anne1" e tipo "CLIENTE"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController
    E o Usuário no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e tipo "CLIENTE"

  Cenario: Put para atualizar Usuário, com erro por senha vazia, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "annef" e senha "   " e tipo "CLIENTE"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController
    E o Usuário no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e tipo "CLIENTE"

  Cenario: Put para atualizar Usuário, com erro por senha maior, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "annef" e senha "anne1234567890123456789012345678901234567890123456789" e tipo "CLIENTE"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 400 do UsuarioController
    E o Usuário no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e tipo "CLIENTE"

  Cenario: Put para atualizar Usuário e remover Endereço (cenário 2), com sucesso, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "james@gmail.com"
    E um UsuarioUpdateDtoRequest, com nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e tipo "CLIENTE"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 200 do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e tipo "CLIENTE"
    E sem EnderecoDtoResponse no body
    E o Usuário no database possui nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e tipo "CLIENTE"
    E sem Endereço salvo no database

  Cenario: Put para atualizar Usuário e criar Endereço (cenário 3), com sucesso, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "jeff@gmail.com"
    E um UsuarioUpdateDtoRequest e EnderecoDtoRequest, com nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e tipo "CLIENTE" e com cep "96065-815" e logradouro "Rua Otto Fassbender Filho" e número "200"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 200 do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e tipo "CLIENTE"
    E com EnderecoDtoResponse no body, com id e cep "96065-815" e logradouro "Rua Otto Fassbender Filho" e número "200"
    E o Usuário no database possui nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e tipo "CLIENTE"
    E um Endereço salvo no database, com cep "96065-815" e logradouro "Rua Otto Fassbender Filho" e número "200"

  Cenario: Put para atualizar Usuário e atualizar Endereço (cenário 4), com sucesso, pelo UsuarioController
    Dado um identificador ID de um usuário existente, com email "james@gmail.com"
    E um UsuarioUpdateDtoRequest e EnderecoDtoRequest, com nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e tipo "PROPRIETARIO" e com cep "68513-224" e logradouro "Quadra Vinte" e número "25"
    Quando uma requisição Put for feita no método update do UsuarioController
    Entao receber ResponseEntity com HTTP 200 do UsuarioController
    E com UsuarioDtoResponse no body, com id e nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e tipo "PROPRIETARIO"
    E com EnderecoDtoResponse no body, com id e cep "68513-224" e logradouro "Quadra Vinte" e número "25"
    E o Usuário no database possui nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e tipo "PROPRIETARIO"
    E um Endereço salvo no database, com cep "68513-224" e logradouro "Quadra Vinte" e número "25"

