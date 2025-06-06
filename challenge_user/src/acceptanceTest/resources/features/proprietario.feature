# language: pt
Funcionalidade: testar operações Create/POST, Read/GET, Update/PUT e Delete/DELETE
  Como Usuário dessa API Rest, desejo cadastrar, consultar, atualizar e deletar Proprietário
  Dessa forma, ter respostas padrão de sucesso ou de erro
  Para saber se meu sistema foi capaz de executar um CRUD de Proprietário

  Contexto:
    Dado ambiente de teste ativado para Proprietário de Challenge_User
    Dado cadastros de Proprietários disponíveis no banco de dados
      |       nome        |         email         |     login   |     senha     |      cep     |  logradouro  |   numero   |        descricao        |
      |  Martin Fowler    |   fowler2@email.com    |   mfowler   |   fowler123   |      null    |      null    |    null    |      gerente geral      |
      |     Kent Beck     |     beck2@proton.me    |     kbeck   |     beck123   |      null    |      null    |    null    |       atendente         |
      |  Jeff Sutherland  |     jeff2@gmail.com    |   jsuther   |   suther234   |      null    |      null    |    null    |       investidor        |
      |    James Clear    |    james2@gmail.com    |    james    |    james12    |   78098-179  |     Rua L    |     300    |        contador         |

  Cenario: Post para criar Proprietário, com sucesso, pelo ProprietarioController
    Dado um ProprietarioDtoRequest, com nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e descricao "advogado"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 201 do ProprietarioController
    E com ProprietarioDtoResponse no body, com id e nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e descricao "advogado"
    E o Proprietario cadastrado no banco de dados possui nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e descricao "advogado"

  Cenario: Post para criar Proprietario e Endereço, com sucesso, pelo ProprietarioController
    Dado um ProprietarioDtoRequest e EnderecoDtoRequest, com nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e descricao "Toda quarta na empresa" e com cep "23520-123" e logradouro "Rua Hermes Lima" e número "700"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 201 do ProprietarioController
    E com ProprietarioDtoResponse no body, com id e nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e descricao "Toda quarta na empresa"
    E com EnderecoDtoResponse no body, com id e cep "23520-123" e logradouro "Rua Hermes Lima" e número "700", pelo ProprietarioController
    E o Proprietario cadastrado no banco de dados possui nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e descricao "Toda quarta na empresa"
    E um Endereço salvo no database, com cep "23520-123" e logradouro "Rua Hermes Lima" e número "700", pelo ProprietarioController

  Cenario: Post para criar Proprietário, com erro por email não único, pelo ProprietarioController
    Dado um ProprietarioDtoRequest, com nome "James Grenning" e email "jeff@gmail.com" e login "grenning" e senha "12345" e descricao "Presente toda quinta-feira"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 409 do ProprietarioController

  Cenario: Post para criar Proprietário, com erro por nome vazio, pelo ProprietarioController
    Dado um ProprietarioDtoRequest, com nome "   " e email "hel@email.com" e login "helga" e senha "12345" e descricao "Visita a empresa quinzenalmente"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController

  Cenario: Post para criar Proprietario, com erro por nome maior, pelo ProprietarioController
    Dado um ProprietarioDtoRequest, com nome "nome grande nome grande nome grande nome grande nome grande nome grande nome grande" e email "hel@email.com" e login "helga" e senha "12345" e descricao "Está na empresa pela manhã"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController

  Cenario: Post para criar Proprietario, com erro por email vazio, pelo ProprietarioController
    Dado um ProprietarioDtoRequest, com nome "Helga Weiss" e email " " e login "helga" e senha "12345" e descricao "Trabalha como atendente"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController

  Cenario: Post para criar Proprietario, com erro por email incorreto, pelo ProprietarioController
    Dado um ProprietarioDtoRequest, com nome "Helga Weiss" e email "helga.com" e login "helga" e senha "12345" e descricao "sócio-investidor"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController

  Cenario: Post para criar Proprietario, com erro por login vazio, pelo ProprietarioController
    Dado um ProprietarioDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "  " e senha "12345" e descricao "turno no período noturno"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController

  Cenario: Post para criar Proprietario, com erro por login maior, pelo ProprietarioController
    Dado um ProprietarioDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "helgaweiss.helgaweiss.helgaweiss.helgaweiss.helgaweiss" e senha "12345" e descricao "Contador da empresa"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController

  Cenario: Post para criar Proprietario, com erro por senha vazia, pelo ProprietarioController
    Dado um ProprietarioDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "helga" e senha "  " e descricao "Gerente de entregas"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController

  Cenario: Post para criar Proprietario, com erro por senha maior, pelo ProprietarioController
    Dado um ProprietarioDtoRequest, com nome "Helga Weiss" e email "hel@email.com" e login "helga" e senha "anne01234567890123456789012345678901234567890123456789" e descricao "1234-6666-012"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController


  Cenario: Get para consultar Proprietario, com sucesso, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "fowler2@email.com"
    Quando uma requisição Get for feita no método findById do ProprietarioController
    Entao receber ResponseEntity com HTTP 200 do ProprietarioController
    E com ProprietarioDtoResponse no body, com id e nome "Martin Fowler" e email "fowler2@email.com" e login "mfowler" e senha "fowler123" e descricao "gerente geral"

  Cenario: Get para consultar Proprietario não encontrado pelo ProprietarioController
    Dado um identificador ID de um proprietario inexistente
    Quando uma requisição Get for feita no método findById do ProprietarioController
    Entao receber ResponseEntity com HTTP 404 do ProprietarioController


  Cenario: Get para pesquisa paginada de Proprietario, com sucesso por um nome, pelo ProprietarioController
    Quando uma requisição Get for feita, com nome "James Clear" no filtro, no método search do ProprietarioController
    Entao receber ResponseEntity com HTTP 200 do ProprietarioController
    E a resposta deve conter apenas proprietario, com nome "James Clear", no método search do ProprietarioController

  Cenario: Get para pesquisa paginada de Proprietario, com sucesso por dois nomes, pelo ProprietarioController
    Quando uma requisição Get for feita, com nome "James Clear,Kent Beck" no filtro, no método search do ProprietarioController
    Entao receber ResponseEntity com HTTP 200 do ProprietarioController
    E a resposta deve conter apenas proprietario, com nome "James Clear,Kent Beck", no método search do ProprietarioController

  Cenario: Get para pesquisa paginada de Proprietario, com sucesso por dois emails, pelo ProprietarioController
    Quando uma requisição Get for feita, com email "jeff2@gmail.com,beck2@proton.me" no filtro, no método search do ProprietarioController
    Entao receber ResponseEntity com HTTP 200 do ProprietarioController
    E a resposta deve conter apenas proprietarios, com email "jeff2@gmail.com,beck2@proton.me", no método search do ProprietarioController

  Cenario: Get para pesquisa paginada de Proprietario, com sucesso por duas descricões, pelo ProprietarioController
    Quando uma requisição Get for feita, com descricao "atendente,contador" no filtro, no método search do ProprietarioController
    Entao receber ResponseEntity com HTTP 200 do ProprietarioController
    E a resposta deve conter apenas proprietarios, com descricao "atendente,contador", no método search do ProprietarioController


  Cenario: Delete para apagar Proprietario, com sucesso, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "beck2@proton.me"
    Quando uma requisição Delete for feita no método deleteById do ProprietarioController
    Entao receber ResponseEntity com HTTP 204 do ProprietarioController
    E o Proprietario foi apagado do banco de dados pelo ProprietarioController

  Cenario: Delete para apagar Proprietario não encontrado pelo ProprietarioController
    Dado um identificador ID de um proprietario inexistente
    Quando uma requisição Delete for feita no método deleteById do ProprietarioController
    Entao receber ResponseEntity com HTTP 404 do ProprietarioController


  Cenario: Put para atualizar Proprietario, com sucesso, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "beck2@proton.me"
    E um ProprietarioDtoRequest, com nome "Kent Beck Jr" e email "kbeck@proton.me" e login "kentbeck" e senha "kbeck12" e descricao "atendente chefe"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 200 do ProprietarioController
    E com ProprietarioDtoResponse no body, com id e nome "Kent Beck Jr" e email "kbeck@proton.me" e login "kentbeck" e senha "kbeck12" e descricao "atendente chefe"
    E o Proprietario cadastrado no banco de dados possui nome "Kent Beck Jr" e email "kbeck@proton.me" e login "kentbeck" e senha "kbeck12" e descricao "atendente chefe"

  Cenario: Put para atualizar Proprietario não encontrado pelo ProprietarioController
    Dado um identificador ID de um proprietario inexistente
    E um ProprietarioDtoRequest, com nome "Viktor Frankl" e email "vik@email.com" e login "viktor" e senha "vikt1" e descricao "É o contador da empresa"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 404 do ProprietarioController

  Cenario: Put para atualizar Proprietario, com erro por nome vazio, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "jeff2@gmail.com"
    E um ProprietarioDtoRequest, com nome "  " e email "anne@email.com" e login "annee" e senha "anne1" e descricao "Contador da empresa"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController
    E o Proprietario no database possui nome "Jeff Sutherland" e email "jeff2@gmail.com" e login "jsuther" e senha "suther234" e descricao "investidor"

  Cenario: Put para atualizar Proprietario, com erro por nome maior, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "jeff2@gmail.com"
    E um ProprietarioDtoRequest, com nome "Anne Frank abcdefghijl abcdefghijl abcdefghijl abcdefghijl abcdefghijl abcdefghijl abcdefghijl" e email "anne@email.com" e login "annee" e senha "anne1" e descricao "Contador da empresa"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController
    E o Proprietario no database possui nome "Jeff Sutherland" e email "jeff2@gmail.com" e login "jsuther" e senha "suther234" e descricao "investidor"

  Cenario: Put para atualizar Proprietario, com erro por email vazio, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "jeff2@gmail.com"
    E um ProprietarioDtoRequest, com nome "Anne Frank" e email "   " e login "anne" e senha "anne1" e descricao "Contador da empresa"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController
    E o Proprietario no database possui nome "Jeff Sutherland" e email "jeff2@gmail.com" e login "jsuther" e senha "suther234" e descricao "investidor"

  Cenario: Put para atualizar Proprietario, com erro por email incorreto, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "jeff2@gmail.com"
    E um ProprietarioDtoRequest, com nome "Anne Frank" e email "anne.com" e login "anne" e senha "anne1" e descricao "Contador da empresa"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController
    E o Proprietario no database possui nome "Jeff Sutherland" e email "jeff2@gmail.com" e login "jsuther" e senha "suther234" e descricao "investidor"

  Cenario: Put para atualizar Proprietario, com erro por login vazio, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "jeff2@gmail.com"
    E um ProprietarioDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "   " e senha "anne1" e descricao "Contador da empresa"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController
    E o Proprietario no database possui nome "Jeff Sutherland" e email "jeff2@gmail.com" e login "jsuther" e senha "suther234" e descricao "investidor"

  Cenario: Put para atualizar Proprietario, com erro por login maior, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "jeff2@gmail.com"
    E um ProprietarioDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "annefrank1annefrank1annefrank1annefrank1annefrank12" e senha "anne1" e descricao "Contador da empresa"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController
    E o Proprietario no database possui nome "Jeff Sutherland" e email "jeff2@gmail.com" e login "jsuther" e senha "suther234" e descricao "investidor"

  Cenario: Put para atualizar Proprietario, com erro por senha vazia, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "jeff2@gmail.com"
    E um ProprietarioDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "annef" e senha "   " e descricao "Contador da empresa"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController
    E o Proprietario no database possui nome "Jeff Sutherland" e email "jeff2@gmail.com" e login "jsuther" e senha "suther234" e descricao "investidor"

  Cenario: Put para atualizar Proprietario, com erro por senha maior, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "jeff2@gmail.com"
    E um ProprietarioDtoRequest, com nome "Anne Frank" e email "anne@email.com" e login "annef" e senha "anne1234567890123456789012345678901234567890123456789" e descricao "Contador da empresa"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 400 do ProprietarioController
    E o Proprietario no database possui nome "Jeff Sutherland" e email "jeff2@gmail.com" e login "jsuther" e senha "suther234" e descricao "investidor"

  Cenario: Put para atualizar Proprietario e remover Endereço (cenário 2), com sucesso, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "james2@gmail.com"
    E um ProprietarioDtoRequest, com nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e descricao "TI da empresa"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 200 do ProprietarioController
    E com ProprietarioDtoResponse no body, com id e nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e descricao "TI da empresa"
    E sem EnderecoDtoResponse no body pelo ProprietarioController
    E o Proprietario no database possui nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e descricao "TI da empresa"
    E sem Endereço salvo no database pelo ProprietarioController

  Cenario: Put para atualizar Proprietario e criar Endereço (cenário 3), com sucesso, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "jeff2@gmail.com"
    E um ProprietarioDtoRequest e EnderecoDtoRequest, com nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e descricao "TI da empresa" e com cep "96065-815" e logradouro "Rua Otto Fassbender Filho" e número "200"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 200 do ProprietarioController
    E com ProprietarioDtoResponse no body, com id e nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e descricao "TI da empresa"
    E com EnderecoDtoResponse no body, com id e cep "96065-815" e logradouro "Rua Otto Fassbender Filho" e número "200", pelo ProprietarioController
    E o Proprietario no database possui nome "J. Sutherland" e email "js@email.com" e login "jeffs" e senha "js123" e descricao "TI da empresa"
    E um Endereço salvo no database, com cep "96065-815" e logradouro "Rua Otto Fassbender Filho" e número "200", pelo ProprietarioController

  Cenario: Put para atualizar Proprietario e atualizar Endereço (cenário 4), com sucesso, pelo ProprietarioController
    Dado um identificador ID de um proprietario existente, com email "james2@gmail.com"
    E um ProprietarioDtoRequest e EnderecoDtoRequest, com nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e descricao "TI da empresa" e com cep "68513-224" e logradouro "Quadra Vinte" e número "25"
    Quando uma requisição Put for feita no método update do ProprietarioController
    Entao receber ResponseEntity com HTTP 200 do ProprietarioController
    E com ProprietarioDtoResponse no body, com id e nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e descricao "TI da empresa"
    E com EnderecoDtoResponse no body, com id e cep "68513-224" e logradouro "Quadra Vinte" e número "25", pelo ProprietarioController
    E o Proprietario no database possui nome "James Clear Jr" e email "clear@email.com" e login "clear" e senha "clear12" e descricao "TI da empresa"
    E um Endereço salvo no database, com cep "68513-224" e logradouro "Quadra Vinte" e número "25", pelo ProprietarioController

