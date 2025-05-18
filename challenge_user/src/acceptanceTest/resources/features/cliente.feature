# language: pt
Funcionalidade: testar operações Create/POST, Read/GET, Update/PUT e Delete/DELETE
  Como Usuário dessa API Rest, desejo cadastrar, consultar, atualizar e deletar Cliente
  Dessa forma, ter respostas padrão de sucesso ou de erro
  Para saber se meu sistema foi capaz de executar um CRUD de Cliente

  Contexto:
    Dado ambiente de teste ativado para Challenge_User
    Dado cadastros de Clientes disponíveis no banco de dados
    |       nome        |         email         |     login   |     senha     |      cep     |  logradouro  |   numero   |  numeroCartaoFidelidade  |
    |  Martin Fowler    |   fowler@email.com    |   mfowler   |   fowler123   |      null    |      null    |    null    |     1234-5555-000        |
    |     Kent Beck     |     beck@proton.me    |     kbeck   |     beck123   |      null    |      null    |    null    |     1234-5555-001        |
    |  Jeff Sutherland  |     jeff@gmail.com    |   jsuther   |   suther234   |      null    |      null    |    null    |     1234-5555-002        |
    |    James Clear    |    james@gmail.com    |    james    |    james12    |   78098-179  |     Rua L    |     300    |     1234-5555-003        |

  Cenario: Post para criar Usuário Cliente, com sucesso, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Robert Martin" e email "rm@email.com" e login "rmartin" e senha "rm123" e numeroCartaoFidelidade "1234-6666-004"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 201 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "Robert Martin" e email "rm@email.com" e login "rmartin" e senha "rm123" e numeroCartaoFidelidade "1234-6666-004"
    E o Cliente cadastrado no banco de dados possui nome "Robert Martin" e email "rm@email.com" e login "rmartin" e senha "rm123" e numeroCartaoFidelidade "1234-6666-004"

#  Cenario: Post para criar Usuário Proprietário, com sucesso, pelo ClienteController
#    Dado um UsuarioDtoRequest, com nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e tipo "PROPRIETARIO"
#    Quando a requisição Post for feita no método create do UsuarioController
#    Entao receber ResponseEntity com HTTP 201 do UsuarioController
#    E com UsuarioDtoResponse no body, com id e nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e tipo "PROPRIETARIO"
#    E o Usuário cadastrado no banco de dados possui nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e tipo "PROPRIETARIO"

  Cenario: Post para criar Usuário Cliente, com erro por nome vazio, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "   " e email "hel@email.com" e login "helga" e senha "12345" e numeroCartaoFidelidade "1234-6666-005"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Usuário Cliente, com erro por nome maior, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "nome grande nome grande nome grande nome grande nome grande nome grande nome grande" e email "hel@email.com" e login "helga" e senha "12345" e numeroCartaoFidelidade "1234-6666-006"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Usuário Cliente, com erro por email vazio, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Helga Weiss" e email " " e login "helga" e senha "12345" e numeroCartaoFidelidade "1234-6666-007"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Usuário Cliente, com erro por email incorreto, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Helga Weiss" e email "helga.com" e login "helga" e senha "12345" e numeroCartaoFidelidade "1234-6666-008"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Usuário Cliente, com erro por login vazio, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "  " e senha "12345" e numeroCartaoFidelidade "1234-6666-009"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Usuário Cliente, com erro por login maior, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "helgaweiss.helgaweiss.helgaweiss.helgaweiss.helgaweiss" e senha "12345" e numeroCartaoFidelidade "1234-6666-010"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Usuário Cliente, com erro por senha vazia, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "helga" e senha "  " e numeroCartaoFidelidade "1234-6666-011"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Usuário Cliente, com erro por senha maior, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "helga" e senha "anne01234567890123456789012345678901234567890123456789" e numeroCartaoFidelidade "1234-6666-012"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Usuário Cliente e Endereço, com sucesso, pelo ClienteController
    Dado um ClienteDtoRequest e EnderecoDtoRequest, com nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e numeroCartaoFidelidade "1234-6666-013" e com cep "23520-123" e logradouro "Rua Hermes Lima" e número "700"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 201 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e numeroCartaoFidelidade "1234-6666-013"
    E com EnderecoDtoResponse no body, com id e cep "23520-123" e logradouro "Rua Hermes Lima" e número "700"
    E o Cliente cadastrado no banco de dados possui nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e numeroCartaoFidelidade "1234-6666-013"
    E um Endereço salvo no database, com cep "23520-123" e logradouro "Rua Hermes Lima" e número "700"


  Cenario: Get para consultar Usuário Cliente, com sucesso, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "fowler@email.com"
    Quando uma requisição Get for feita no método findById do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "Martin Fowler" e email "fowler@email.com" e login "mfowler" e senha "fowler123" e numeroCartaoFidelidade "1234-5555-000"

  Cenario: Get para consultar Usuário Cliente não encontrado pelo ClienteController
    Dado um identificador ID de um cliente inexistente
    Quando uma requisição Get for feita no método findById do ClienteController
    Entao receber ResponseEntity com HTTP 404 do ClienteController


  Cenario: Delete para apagar Usuário Cliente, com sucesso, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "beck@proton.me"
    Quando uma requisição Delete for feita no método deleteById do ClienteController
    Entao receber ResponseEntity com HTTP 204 do ClienteController
    E o Cliente foi apagado do banco de dados pelo ClienteController

  Cenario: Delete para apagar Usuário Cliente não encontrado pelo ClienteController
    Dado um identificador ID de um cliente inexistente
    Quando uma requisição Delete for feita no método deleteById do ClienteController
    Entao receber ResponseEntity com HTTP 404 do ClienteController


  Cenario: Put para atualizar Usuário Cliente, com sucesso, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "jeff@gmail.com"
    E um ClienteUpdateDtoRequest, com nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e numeroCartaoFidelidade "1234-8888-001"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e numeroCartaoFidelidade "1234-8888-001"
    E o Cliente cadastrado no banco de dados possui nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e numeroCartaoFidelidade "1234-8888-001"

#  Cenario: Put para atualizar Usuário Proprietário, com sucesso, pelo UsuarioController
#    Dado um identificador ID de um usuário existente, com email "beck@proton.me"
#    E um UsuarioUpdateDtoRequest, com nome "Kent Beck Jr" e email "kbeck@proton.me" e login "kentbeck" e senha "kbeck12" e tipo "CLIENTE"
#    Quando uma requisição Put for feita no método update do UsuarioController
#    Entao receber ResponseEntity com HTTP 200 do UsuarioController
#    E com UsuarioDtoResponse no body, com id e nome "Kent Beck Jr" e email "kbeck@proton.me" e login "kentbeck" e senha "kbeck12" e tipo "CLIENTE"
#    E o Usuário cadastrado no banco de dados possui nome "Kent Beck Jr" e email "kbeck@proton.me" e login "kentbeck" e senha "kbeck12" e tipo "CLIENTE"

  Cenario: Put para atualizar Cliente não encontrado pelo ClienteController
    Dado um identificador ID de um cliente inexistente
    E um ClienteUpdateDtoRequest, com nome "Viktor Frankl" e email "vik@email.com" e login "viktor" e senha "vikt1" e numeroCartaoFidelidade "1234-8888-002"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 404 do ClienteController

  Cenario: Put para atualizar Cliente, com erro por nome vazio, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "jeff@gmail.com"
    E um ClienteUpdateDtoRequest, com nome "  " e email "anne@email.com" e login "annee" e senha "anne1" e numeroCartaoFidelidade "1234-8888-002"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por nome maior, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "jeff@gmail.com"
    E um ClienteUpdateDtoRequest, com nome "Anne Frank abcdefghijl abcdefghijl abcdefghijl abcdefghijl abcdefghijl abcdefghijl abcdefghijl" e email "anne@email.com" e login "annee" e senha "anne1" e numeroCartaoFidelidade "1234-8888-003"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por email vazio, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "jeff@gmail.com"
    E um ClienteUpdateDtoRequest, com nome "Anne Frank" e email "   " e login "anne" e senha "anne1" e numeroCartaoFidelidade "1234-8888-004"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por email incorreto, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "jeff@gmail.com"
    E um ClienteUpdateDtoRequest, com nome "Anne Frank" e email "anne.com" e login "anne" e senha "anne1" e numeroCartaoFidelidade "1234-8888-005"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por login vazio, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "jeff@gmail.com"
    E um ClienteUpdateDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "   " e senha "anne1" e numeroCartaoFidelidade "1234-8888-006"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por login maior, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "jeff@gmail.com"
    E um ClienteUpdateDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "annefrank1annefrank1annefrank1annefrank1annefrank12" e senha "anne1" e numeroCartaoFidelidade "1234-8888-007"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por senha vazia, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "jeff@gmail.com"
    E um ClienteUpdateDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "annef" e senha "   " e numeroCartaoFidelidade "1234-8888-008"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por senha maior, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "jeff@gmail.com"
    E um ClienteUpdateDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "annef" e senha "anne1234567890123456789012345678901234567890123456789" e numeroCartaoFidelidade "1234-8888-009"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Jeff Sutherland" e email "jeff@gmail.com" e login "jsuther" e senha "suther234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente e remover Endereço (cenário 2), com sucesso, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "james@gmail.com"
    E um ClienteUpdateDtoRequest, com nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e numeroCartaoFidelidade "1234-8888-0010"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e numeroCartaoFidelidade "1234-8888-0010"
    E sem EnderecoDtoResponse no body
    E o Cliente no database possui nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e numeroCartaoFidelidade "1234-8888-0010"
    E sem Endereço salvo no database

  Cenario: Put para atualizar Cliente e criar Endereço (cenário 3), com sucesso, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "jeff@gmail.com"
    E um ClienteUpdateDtoRequest e EnderecoDtoRequest, com nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e numeroCartaoFidelidade "1234-8888-0011" e com cep "96065-815" e logradouro "Rua Otto Fassbender Filho" e número "200"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e numeroCartaoFidelidade "1234-8888-0011"
    E com EnderecoDtoResponse no body, com id e cep "96065-815" e logradouro "Rua Otto Fassbender Filho" e número "200"
    E o Cliente no database possui nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e numeroCartaoFidelidade "1234-8888-0011"
    E um Endereço salvo no database, com cep "96065-815" e logradouro "Rua Otto Fassbender Filho" e número "200"

  Cenario: Put para atualizar Cliente e atualizar Endereço (cenário 4), com sucesso, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "james@gmail.com"
    E um ClienteUpdateDtoRequest e EnderecoDtoRequest, com nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e numeroCartaoFidelidade "1234-8888-0012" e com cep "68513-224" e logradouro "Quadra Vinte" e número "25"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e numeroCartaoFidelidade "1234-8888-0012"
    E com EnderecoDtoResponse no body, com id e cep "68513-224" e logradouro "Quadra Vinte" e número "25"
    E o Cliente no database possui nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e numeroCartaoFidelidade "1234-8888-0012"
    E um Endereço salvo no database, com cep "68513-224" e logradouro "Quadra Vinte" e número "25"

