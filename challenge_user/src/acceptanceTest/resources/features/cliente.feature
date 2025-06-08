# language: pt
Funcionalidade: testar operações Create/POST, Read/GET, Update/PUT e Delete/DELETE
  Como Usuário dessa API Rest, desejo cadastrar, consultar, atualizar e deletar Cliente
  Dessa forma, ter respostas padrão de sucesso ou de erro
  Para saber se meu sistema foi capaz de executar um CRUD de Cliente

  Contexto:
    Dado ambiente de teste ativado para Challenge_User
    Dado cadastros de Clientes disponíveis no banco de dados
    |       nome        |         email         |    login   |     senha     |      cep     |  logradouro  |   numero   |  numeroCartaoFidelidade  |
    |    Mike Beedle    |     mike@email.com    |    mikeb   |    mike123    |      null    |      null    |    null    |     1234-5555-000        |
    | Arie van Bennekum |     arie@proton.me    |    ariev   |    arie123    |      null    |      null    |    null    |     1234-5555-001        |
    |  Ward Cunningham  |     ward@gmail.com    |    wardc   |    ward234    |      null    |      null    |    null    |     1234-5555-002        |
    |  James Grenning   |    james@gmail.com    |    james   |    james12    |   78098-179  |     Rua L    |     300    |     1234-5555-003        |

  Cenario: Post para criar Cliente, com sucesso, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Jim Highsmith" e email "jim@email.com" e login "highsmith" e senha "high123" e numeroCartaoFidelidade "1234-6666-004"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 201 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "Jim Highsmith" e email "jim@email.com" e login "highsmith" e senha "high123" e numeroCartaoFidelidade "1234-6666-004"
    E o Cliente cadastrado no banco de dados possui nome "Jim Highsmith" e email "jim@email.com" e login "highsmith" e senha "high123" e numeroCartaoFidelidade "1234-6666-004"

  Cenario: Post para criar Cliente e Endereço, com sucesso, pelo ClienteController
    Dado um ClienteDtoRequest e EnderecoDtoRequest, com nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e numeroCartaoFidelidade "1234-6666-013" e com cep "23520-123" e logradouro "Rua Hermes Lima" e número "700"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 201 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e numeroCartaoFidelidade "1234-6666-013"
    E com EnderecoDtoResponse no body, com id e cep "23520-123" e logradouro "Rua Hermes Lima" e número "700"
    E o Cliente cadastrado no banco de dados possui nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e numeroCartaoFidelidade "1234-6666-013"
    E um Endereço salvo no database, com cep "23520-123" e logradouro "Rua Hermes Lima" e número "700"

  Cenario: Post para criar Cliente, com erro por email não único, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Alistair Cockburnn" e email "mike@email.com" e login "alistair" e senha "12345" e numeroCartaoFidelidade "1234-6666-987"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 409 do ClienteController

  Cenario: Post para criar Cliente, com erro por login não único, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Alistair Cockburnnn" e email "cock@email.com" e login "mikeb" e senha "12345" e numeroCartaoFidelidade "1234-6666-987"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 409 do ClienteController

  Cenario: Post para criar Cliente, com erro por nome não único, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Mike Beedle" e email "cock@email.com" e login "alistair" e senha "12345" e numeroCartaoFidelidade "1234-6666-987"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 409 do ClienteController

  Cenario: Post para criar Cliente, com erro por nome vazio, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "   " e email "hel@email.com" e login "helga" e senha "12345" e numeroCartaoFidelidade "1234-6666-005"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Cliente, com erro por nome maior, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "nome grande nome grande nome grande nome grande nome grande nome grande nome grande" e email "hel@email.com" e login "helga" e senha "12345" e numeroCartaoFidelidade "1234-6666-006"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Cliente, com erro por email vazio, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Helga Weiss" e email " " e login "helga" e senha "12345" e numeroCartaoFidelidade "1234-6666-007"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Cliente, com erro por email incorreto, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Helga Weiss" e email "helga.com" e login "helga" e senha "12345" e numeroCartaoFidelidade "1234-6666-008"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Cliente, com erro por login vazio, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "  " e senha "12345" e numeroCartaoFidelidade "1234-6666-009"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Cliente, com erro por login maior, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "helgaweiss.helgaweiss.helgaweiss.helgaweiss.helgaweiss" e senha "12345" e numeroCartaoFidelidade "1234-6666-010"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Cliente, com erro por senha vazia, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "helga" e senha "  " e numeroCartaoFidelidade "1234-6666-011"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController

  Cenario: Post para criar Cliente, com erro por senha maior, pelo ClienteController
    Dado um ClienteDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "helga" e senha "anne01234567890123456789012345678901234567890123456789" e numeroCartaoFidelidade "1234-6666-012"
    Quando a requisição Post for feita no método create do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController


  Cenario: Get para consultar Cliente, com sucesso, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "mike@email.com"
    Quando uma requisição Get for feita no método findById do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "Mike Beedle" e email "mike@email.com" e login "mikeb" e senha "mike123" e numeroCartaoFidelidade "1234-5555-000"

  Cenario: Get para consultar Cliente não encontrado pelo ClienteController
    Dado um identificador ID de um cliente inexistente
    Quando uma requisição Get for feita no método findById do ClienteController
    Entao receber ResponseEntity com HTTP 404 do ClienteController


  Cenario: Get para pesquisa paginada de Cliente, com sucesso por um nome, pelo ClienteController
    Quando uma requisição Get for feita, com nome "Mike Beedle" no filtro, no método search do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E a resposta deve conter apenas clientes, com nome "Mike Beedle", no método search do ClienteController

  Cenario: Get para pesquisa paginada de Cliente, com sucesso por dois nomes, pelo ClienteController
    Quando uma requisição Get for feita, com nome "James Grenning,Mike Beedle" no filtro, no método search do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E a resposta deve conter apenas clientes, com nome "James Grenning,Mike Beedle", no método search do ClienteController

  Cenario: Get para pesquisa paginada de Cliente, com sucesso por dois emails, pelo ClienteController
    Quando uma requisição Get for feita, com email "ward@gmail.com,arie@proton.me" no filtro, no método search do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E a resposta deve conter apenas clientes, com email "ward@gmail.com,arie@proton.me", no método search do ClienteController

  Cenario: Get para pesquisa paginada de Cliente, com sucesso por dois numeroCartaoFidelidade, pelo ClienteController
    Quando uma requisição Get for feita, com numeroCartaoFidelidade "1234-5555-003,1234-5555-001" no filtro, no método search do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E a resposta deve conter apenas clientes, com numeroCartaoFidelidade "1234-5555-003,1234-5555-001", no método search do ClienteController


  Cenario: Put para atualizar Cliente, com sucesso, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "arie@proton.me"
    E um ClienteDtoRequest, com nome "Arie v. Bennekum" e email "arievb@proton.me" e login "arievb" e senha "arievb123" e numeroCartaoFidelidade "1234-8888-001"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "Arie v. Bennekum" e email "arievb@proton.me" e login "arievb" e senha "arievb123" e numeroCartaoFidelidade "1234-8888-001"
    E o Cliente cadastrado no banco de dados possui nome "Arie v. Bennekum" e email "arievb@proton.me" e login "arievb" e senha "arievb123" e numeroCartaoFidelidade "1234-8888-001"

  Cenario: Put para atualizar Cliente não encontrado pelo ClienteController
    Dado um identificador ID de um cliente inexistente
    E um ClienteDtoRequest, com nome "Viktor Frankl" e email "vik@email.com" e login "viktor" e senha "vikt1" e numeroCartaoFidelidade "1234-8888-002"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 404 do ClienteController

  Cenario: Put para atualizar Cliente, com erro por nome vazio, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "ward@gmail.com"
    E um ClienteDtoRequest, com nome "  " e email "wardcunn@gmail.com" e login "wardcunn" e senha "wardcunn234" e numeroCartaoFidelidade "5555-8888-002"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Ward Cunningham" e email "ward@gmail.com" e login "wardc" e senha "ward234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por nome maior, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "ward@gmail.com"
    E um ClienteDtoRequest, com nome "Ward Cunningham abcdefghijl abcdefghijl abcdefghijl abcdefghijl abcdefghijl abcdefghijl abcdefghijl" e email "wardcunn@gmail.com" e login "wardcunn" e senha "wardcunn234" e numeroCartaoFidelidade "5555-8888-002"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Ward Cunningham" e email "ward@gmail.com" e login "wardc" e senha "ward234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por email vazio, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "ward@gmail.com"
    E um ClienteDtoRequest, com nome "Ward Cunninghammm" e email "   " e login "wardcunn" e senha "wardcunn234" e numeroCartaoFidelidade "5555-8888-002"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Ward Cunningham" e email "ward@gmail.com" e login "wardc" e senha "ward234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por email incorreto, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "ward@gmail.com"
    E um ClienteDtoRequest, com nome "Ward Cunninghammm" e email "ward.com" e login "wardcunn" e senha "wardcunn234" e numeroCartaoFidelidade "5555-8888-002"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Ward Cunningham" e email "ward@gmail.com" e login "wardc" e senha "ward234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por login vazio, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "ward@gmail.com"
    E um ClienteDtoRequest, com nome "Ward Cunninghammm" e email "wardc@gmail.com" e login "   " e senha "wardcunn234" e numeroCartaoFidelidade "5555-8888-002"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Ward Cunningham" e email "ward@gmail.com" e login "wardc" e senha "ward234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por login maior, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "ward@gmail.com"
    E um ClienteDtoRequest, com nome "Ward Cunninghammm" e email "wardc@gmail.com" e login "wardctestetestetestetestetestetestetestetestetestetestetesteteste" e senha "wardcunn234" e numeroCartaoFidelidade "5555-8888-002"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Ward Cunningham" e email "ward@gmail.com" e login "wardc" e senha "ward234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por senha vazia, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "ward@gmail.com"
    E um ClienteDtoRequest, com nome "Ward Cunninghammm" e email "wardc@gmail.com" e login "wardcunn" e senha "   " e numeroCartaoFidelidade "5555-8888-002"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Ward Cunningham" e email "ward@gmail.com" e login "wardc" e senha "ward234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente, com erro por senha maior, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "ward@gmail.com"
    E um ClienteDtoRequest, com nome "Ward Cunninghammm" e email "wardc@gmail.com" e login "wardcunn" e senha "anne1234567890123456789012345678901234567890123456789" e numeroCartaoFidelidade "5555-8888-002"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 400 do ClienteController
    E o Cliente no database possui nome "Ward Cunningham" e email "ward@gmail.com" e login "wardc" e senha "ward234" e numeroCartaoFidelidade "1234-5555-002"

  Cenario: Put para atualizar Cliente e remover Endereço (cenário 2), com sucesso, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "james@gmail.com"
    E um ClienteDtoRequest, com nome "James Grenning Jr" e email "jamesg@gmail.com" e login "jamesg" e senha "james12" e numeroCartaoFidelidade "1234-8888-1010"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "James Grenning Jr" e email "jamesg@gmail.com" e login "jamesg" e senha "james12" e numeroCartaoFidelidade "1234-8888-1010"
    E sem EnderecoDtoResponse no body
    E o Cliente no database possui nome "James Grenning Jr" e email "jamesg@gmail.com" e login "jamesg" e senha "james12" e numeroCartaoFidelidade "1234-8888-1010"
    E sem Endereço salvo no database

  Cenario: Put para atualizar Cliente e criar Endereço (cenário 3), com sucesso, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "james@gmail.com"
    E um ClienteDtoRequest e EnderecoDtoRequest, com nome "James Grenning Jr" e email "jamesg@gmail.com" e login "jamesg" e senha "james12" e numeroCartaoFidelidade "1234-8888-1010" e com cep "96065-815" e logradouro "Rua Otto Fassbender Filho" e número "200"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "James Grenning Jr" e email "jamesg@gmail.com" e login "jamesg" e senha "james12" e numeroCartaoFidelidade "1234-8888-1010"
    E com EnderecoDtoResponse no body, com id e cep "96065-815" e logradouro "Rua Otto Fassbender Filho" e número "200"
    E o Cliente no database possui nome "James Grenning Jr" e email "jamesg@gmail.com" e login "jamesg" e senha "james12" e numeroCartaoFidelidade "1234-8888-1010"
    E um Endereço salvo no database, com cep "96065-815" e logradouro "Rua Otto Fassbender Filho" e número "200"

  Cenario: Put para atualizar Cliente e atualizar Endereço (cenário 4), com sucesso, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "james@gmail.com"
    E um ClienteDtoRequest e EnderecoDtoRequest, com nome "James Grenning Jr" e email "jamesg@gmail.com" e login "jamesg" e senha "james12" e numeroCartaoFidelidade "1234-8888-1010" e com cep "68513-224" e logradouro "Quadra Vinte" e número "25"
    Quando uma requisição Put for feita no método update do ClienteController
    Entao receber ResponseEntity com HTTP 200 do ClienteController
    E com ClienteDtoResponse no body, com id e nome "James Grenning Jr" e email "jamesg@gmail.com" e login "jamesg" e senha "james12" e numeroCartaoFidelidade "1234-8888-1010"
    E com EnderecoDtoResponse no body, com id e cep "68513-224" e logradouro "Quadra Vinte" e número "25"
    E o Cliente no database possui nome "James Grenning Jr" e email "jamesg@gmail.com" e login "jamesg" e senha "james12" e numeroCartaoFidelidade "1234-8888-1010"
    E um Endereço salvo no database, com cep "68513-224" e logradouro "Quadra Vinte" e número "25"


  Cenario: Delete para apagar Cliente, com sucesso, pelo ClienteController
    Dado um identificador ID de um cliente existente, com email "james@gmail.com"
    Quando uma requisição Delete for feita no método deleteById do ClienteController
    Entao receber ResponseEntity com HTTP 204 do ClienteController
    E o Cliente foi apagado do banco de dados pelo ClienteController

  Cenario: Delete para apagar Cliente não encontrado pelo ClienteController
    Dado um identificador ID de um cliente inexistente
    Quando uma requisição Delete for feita no método deleteById do ClienteController
    Entao receber ResponseEntity com HTTP 404 do ClienteController

