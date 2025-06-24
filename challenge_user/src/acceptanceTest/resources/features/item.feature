# language: pt
Funcionalidade: testar operações Create/POST, Read/GET, Update/PUT e Delete/DELETE
  Como Usuário dessa API Rest, desejo cadastrar, consultar, atualizar e deletar Item de Cardápio
  Dessa forma, ter respostas padrão de sucesso ou de erro
  Para saber se meu sistema foi capaz de executar um CRUD de Item de Cardápio

  Contexto:
    Dado ambiente de teste ativado para Item de Challenge_User
    Dado cadastros de Itens disponíveis no banco de dados para ItemController
    |     nome     |        descricao        |   preco   |   entrega   |            foto            |
    |    Sprite    |      Refrigerante       |   14.50   |    false    |  http://link-foto.com.br   |
    |   Coca-Cola  |      Refrigerante       |   22.00   |    true     |  http://link-foto.com.br   |

  Cenario: Post para criar Item, com sucesso, pelo ItemController
    Dado um ItemDtoRequest, com nome "Coca-Cola" e descricao "Refrigerante" e preco "20.00" e entrega "true" e foto "http://link-foto.com.br"
    Quando a requisição Post for feita no método create de ItemController
    Entao receber ResponseEntity com HTTP 201 do ItemController
    E com ItemDtoResponse no body, com id e nome "Coca-Cola" e descricao "Refrigerante" e preco "20.00" e entrega "true" e foto "http://link-foto.com.br"
    E com Item salvo no database, com nome "Coca-Cola" e descricao "Refrigerante" e preco "20.00" e entrega "true" e foto "http://link-foto.com.br"


  Cenario: Get para consultar Item, com sucesso, pelo ItemController
    Dado um identificador ID de um Item existente, com nome "Coca-Cola"
    Quando uma requisição Get for feita no método findById do ItemController
    Entao receber ResponseEntity com HTTP 200 do ItemController
    E com ItemDtoResponse no body, com id e nome "Coca-Cola" e descricao "Refrigerante" e preco "22.00" e entrega "true" e foto "http://link-foto.com.br"


  Cenario: Delete para apagar Item, com sucesso, pelo ItemController
    Dado um identificador ID de um Item existente, com nome "Sprite"
    Quando uma requisição Delete for feita no método deleteById do ItemController
    Entao receber ResponseEntity com HTTP 204 do ItemController
    E o Item foi apagado do banco de dados pelo ItemController


  Cenario: Put para atualizar Item, com sucesso, pelo ItemController
    Dado um identificador ID de um Item existente, com nome "Coca-Cola"
    E um ItemDtoRequest, com nome "Coca-Cola Light" e descricao "Refrigerante Diet" e preco "16.00" e entrega "false" e foto "http://link-fotos.com.br"
    Quando uma requisição Put for feita no método update do ItemController
    Entao receber ResponseEntity com HTTP 200 do ItemController
    E com ItemDtoResponse no body, com id e nome "Coca-Cola Light" e descricao "Refrigerante Diet" e preco "16.00" e entrega "false" e foto "http://link-fotos.com.br"
    E com Item salvo no database, com nome "Coca-Cola Light" e descricao "Refrigerante Diet" e preco "16.00" e entrega "false" e foto "http://link-fotos.com.br"

