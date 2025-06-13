# language: pt
Funcionalidade: testar operações Create/POST, Read/GET, Update/PUT e Delete/DELETE
  Como Usuário dessa API Rest, desejo cadastrar, consultar, atualizar e deletar Restaurante
  Dessa forma, ter respostas padrão de sucesso ou de erro
  Para saber se meu sistema foi capaz de executar um CRUD de Restaurante

  Contexto:
    Dado ambiente de teste ativado para Restaurante de Challenge_User
    Dado cadastros de Clientes disponíveis no banco de dados para RestauranteController
      |       nome        |         email         |    login     |    senha      |  numeroCartaoFidelidade  |
      |   Isaac Newton    |    newton@email.com   |    newton    |   newton123   |     8000-5555-000        |
      |  Albert Einstein  |   einstein@proton.me  |   einstein   |  einstein123  |     8001-5555-011        |
    Dado cadastros de Proprietários disponíveis no banco de dados para RestauranteController
      |       nome        |         email         |    login     |    senha      |  descricao  |
      |  Galileu Galilei  |    galilei@yahoo.com  |    galilei   |   galilei123  |   Teste1    |
      |     Niels Bohr    |      bohr@yahoo.com   |     bohr     |    bohr123    |   Teste2    |
    Dado cadastros de Restaurantes disponíveis no banco de dados
      |         nome        |
      |    Casa do Porco    |
      |      Coco Bambu     |
      |        Fasano       |
      |        D.O.M        |

  Cenario: Post para criar Restaurante, com sucesso, pelo RestauranteController
    Dado um RestauranteDtoRequest, com nome "Arturito"
    Quando a requisição Post for feita no método create do RestauranteController
    Entao receber ResponseEntity com HTTP 201 do RestauranteController
    E com RestauranteDtoResponse no body, com id e nome "Arturito"
    E o Restaurante cadastrado no banco de dados possui nome "Arturito"

  Cenario: Post para criar Restaurante, com erro por nome vazio, pelo RestauranteController
    Dado um RestauranteDtoRequest, com nome "   "
    Quando a requisição Post for feita no método create do RestauranteController
    Entao receber ResponseEntity com HTTP 400 do RestauranteController


  Cenario: Put para atualizar Restaurante, com sucesso, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "Fasano"
    E um RestauranteDtoRequest, com nome "Fasano Atualizado"
    Quando uma requisição Put for feita no método update do RestauranteController
    Entao receber ResponseEntity com HTTP 200 do RestauranteController
    E com RestauranteDtoResponse no body, com id e nome "Fasano Atualizado"
    E o Restaurante cadastrado no banco de dados possui nome "Fasano Atualizado"

  Cenario: Put para atualizar Restaurante, com erro not found, pelo RestauranteController
    Dado um identificador ID de um Restaurante inexistente
    E um RestauranteDtoRequest, com nome "Arturito"
    Quando uma requisição Put for feita no método update do RestauranteController
    Entao receber ResponseEntity com HTTP 404 do RestauranteController

  Cenario: Put para atualizar Restaurante, com erro por nome vazio, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "D.O.M"
    E um RestauranteDtoRequest, com nome "   "
    Quando uma requisição Put for feita no método update do RestauranteController
    Entao receber ResponseEntity com HTTP 400 do RestauranteController
    E o Restaurante cadastrado no database possui nome "D.O.M"


  Cenario: Get para consultar Restaurante, com sucesso, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "Casa do Porco"
    Quando uma requisição Get for feita no método findById do RestauranteController
    Entao receber ResponseEntity com HTTP 200 do RestauranteController
    E com RestauranteDtoResponse no body, com id e nome "Casa do Porco"

  Cenario: Get para consultar Restaurante, com erro not found, pelo RestauranteController
    Dado um identificador ID de um Restaurante inexistente
    Quando uma requisição Get for feita no método findById do RestauranteController
    Entao receber ResponseEntity com HTTP 404 do RestauranteController


  Cenario: Delete para apagar Restaurante, com sucesso, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "Coco Bambu"
    Quando uma requisição Delete for feita no método deleteById do RestauranteController
    Entao receber ResponseEntity com HTTP 204 do RestauranteController
    E o Restaurante foi apagado do banco de dados pelo RestauranteController

  Cenario: Delete para apagar Restaurante, com erro not found, pelo RestauranteController
    Dado um identificador ID de um Restaurante inexistente
    Quando uma requisição Delete for feita no método deleteById do RestauranteController
    Entao receber ResponseEntity com HTTP 404 do RestauranteController

