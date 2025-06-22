# language: pt
Funcionalidade: testar operações Create/POST, Read/GET, Update/PUT e Delete/DELETE
  Como Usuário dessa API Rest, desejo cadastrar, consultar, atualizar e deletar Item de Cardápio
  Dessa forma, ter respostas padrão de sucesso ou de erro
  Para saber se meu sistema foi capaz de executar um CRUD de Item de Cardápio

  Contexto:
    Dado ambiente de teste ativado para Item de Challenge_User

  Cenario: Post para criar Item, com sucesso, pelo ItemController
    Dado um ItemDtoRequest, como nome "Coca-Cola" e descricao "Refrigerante" e preco "20.00" e entrega "true" e foto "http://link-foto.com.br"
    Quando a requisição Post for feita no método create de ItemController
    Entao receber ResponseEntity com HTTP 201 do ItemController
    E com ItemDtoResponse no body, com id e nome "Coca-Cola" e descricao "Refrigerante" e preco "20.00" e entrega "true" e foto "http://link-foto.com.br"
    E com Item salvo no database, com nome "Coca-Cola" e descricao "Refrigerante" e preco "20.00" e entrega "true" e foto "http://link-foto.com.br"


  Cenario: Get para consultar Item, com sucesso, pelo ItemController
    Dado um identificador ID de um Item existente, com nome "Coca-Cola"
    Quando uma requisição Get for feita no método findById do ItemController
    Entao receber ResponseEntity com HTTP 200 do ItemController
    E com ItemDtoResponse no body, com id e nome "Coca-Cola" e descricao "Refrigerante" e preco "20.00" e entrega "true" e foto "http://link-foto.com.br"




