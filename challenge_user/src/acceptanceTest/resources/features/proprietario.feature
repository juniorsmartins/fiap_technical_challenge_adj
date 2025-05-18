# language: pt
Funcionalidade: testar operações Create/POST, Read/GET, Update/PUT e Delete/DELETE
  Como Usuário dessa API Rest, desejo cadastrar, consultar, atualizar e deletar Proprietário
  Dessa forma, ter respostas padrão de sucesso ou de erro
  Para saber se meu sistema foi capaz de executar um CRUD de Proprietário

  Contexto:
    Dado ambiente de teste ativado para Proprietário de Challenge_User
    Dado cadastros de Proprietários disponíveis no banco de dados
      |       nome        |         email         |     login   |     senha     |      cep     |  logradouro  |   numero   |        descricao        |
      |  Martin Fowler    |   fowler@email.com    |   mfowler   |   fowler123   |      null    |      null    |    null    |      gerente geral      |
      |     Kent Beck     |     beck@proton.me    |     kbeck   |     beck123   |      null    |      null    |    null    |       atendente         |
      |  Jeff Sutherland  |     jeff@gmail.com    |   jsuther   |   suther234   |      null    |      null    |    null    |       investidor        |
      |    James Clear    |    james@gmail.com    |    james    |    james12    |   78098-179  |     Rua L    |     300    |        contador         |

  Cenario: Post para criar Proprietário, com sucesso, pelo ProprietarioController
    Dado um ProprietarioDtoRequest, com nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e descricao "advogado"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 201 do ProprietarioController
    E com ProprietarioDtoResponse no body, com id e nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e descricao "advogado"
    E o Proprietario cadastrado no banco de dados possui nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e descricao "advogado"

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

  Cenario: Post para criar Proprietario e Endereço, com sucesso, pelo ProprietarioController
    Dado um ProprietarioDtoRequest e EnderecoDtoRequest, com nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e descricao "Toda quarta na empresa" e com cep "23520-123" e logradouro "Rua Hermes Lima" e número "700"
    Quando a requisição Post for feita no método create do ProprietarioController
    Entao receber ResponseEntity com HTTP 201 do ProprietarioController
    E com ProprietarioDtoResponse no body, com id e nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e descricao "Toda quarta na empresa"
    E com EnderecoDtoResponse no body, com id e cep "23520-123" e logradouro "Rua Hermes Lima" e número "700", pelo ProprietarioController
    E o Proprietario cadastrado no banco de dados possui nome "Geoffrey Blaney" e email "blaney@email.com" e login "blaney" e senha "blaney12" e descricao "Toda quarta na empresa"
    E um Endereço salvo no database, com cep "23520-123" e logradouro "Rua Hermes Lima" e número "700", pelo ProprietarioController




