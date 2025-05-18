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

#  Cenario: Post para criar Proprietário, com sucesso, pelo ProprietarioController
#    Dado um ProprietarioDtoRequest, com nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e descricao "advogado"
#    Quando a requisição Post for feita no método create do ProprietarioController
#    Entao receber ResponseEntity com HTTP 201 do ProprietarioController
#    E com ProprietarioDtoResponse no body, com id e nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e descricao "advogado"
#    E o Proprietario cadastrado no banco de dados possui nome "Alistair Cockburn" e email "cock@email.com" e login "cockburn" e senha "cock12" e descricao "advogado"
