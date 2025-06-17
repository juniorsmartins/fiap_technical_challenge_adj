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
      |  Stephen Hawking  |    hawking@yahoo.com  |    hawking   |   hawking123  |   Teste0    |
      |     Sócrates      |   socrates@yahoo.com  |   socrates   |  socrates123  |   Teste1    |
      |      Platão       |    platao@yahoo.com   |    platao    |   platao123   |   Teste2    |
      |    Aristóteles    | aristoteles@yahoo.com |  aristoteles | aristoteles12 |   Teste3    |
      |  Galileu Galilei  |    galilei@yahoo.com  |    galilei   |   galilei123  |   Teste4    |
      |     Niels Bohr    |      bohr@yahoo.com   |     bohr     |    bohr123    |   Teste5    |
    Dado cadastros de Restaurantes disponíveis no banco de dados
      |         nome        |      cep     |  logradouro  |   numero   |     proprietario      |
      |    Casa do Porco    |   78008-009  |    Rua GH    |    1506    |   hawking@yahoo.com   |
      |      Coco Bambu     |   78511-876  |    Rua BN    |    2234    |  socrates@yahoo.com   |
      |        Fasano       |   78689-123  |    Rua MS    |    3349    |   platao@yahoo.com    |
      |        D.O.M        |   78543-400  |    Rua DG    |    1201    | aristoteles@yahoo.com |

  Cenario: Post para criar Restaurante e Endereço, com sucesso, pelo RestauranteController
    Dado um RestauranteDtoRequest, com nome "Arturito", e EnderecoDtoRequest, com cep "25444-222" e logradouro "Rua Avelino Popo" e número "550",e Proprietario, com email "galilei@yahoo.com"
    Quando a requisição Post for feita no método create do RestauranteController
    Entao receber ResponseEntity com HTTP 201 do RestauranteController
    E com RestauranteDtoResponse no body, com id e nome "Arturito"
    E com EnderecoDtoResponse no body, com id e cep "25444-222" e logradouro "Rua Avelino Popo" e número "550", pelo RestauranteController
    E o Restaurante cadastrado no banco de dados possui nome "Arturito"
    E um Endereço salvo no database, com cep "25444-222" e logradouro "Rua Avelino Popo" e número "550", pelo RestauranteController

  Cenario: Post para criar Restaurante e Endereço, com erro por nome vazio, pelo RestauranteController
    Dado um RestauranteDtoRequest, com nome "   ", e EnderecoDtoRequest, com cep "25444-222" e logradouro "Rua Avelino Popo" e número "550",e Proprietario, com email "galilei@yahoo.com"
    Quando a requisição Post for feita no método create do RestauranteController
    Entao receber ResponseEntity com HTTP 400 do RestauranteController


  Cenario: Put para atualizar Restaurante e Endereço, com sucesso, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "Fasano"
    E um RestauranteDtoRequest, com nome "Fasano Atualizado", e EnderecoDtoRequest, com cep "11333-444" e logradouro "Rua Fasano Central" e número "56",e Proprietario, com email "bohr@yahoo.com"
    Quando uma requisição Put for feita no método update do RestauranteController
    Entao receber ResponseEntity com HTTP 200 do RestauranteController
    E com RestauranteDtoResponse no body, com id e nome "Fasano Atualizado"
    E com EnderecoDtoResponse no body, com id e cep "11333-444" e logradouro "Rua Fasano Central" e número "56", pelo RestauranteController
    E o Restaurante cadastrado no banco de dados possui nome "Fasano Atualizado"
    E um Endereço salvo no database, com cep "11333-444" e logradouro "Rua Fasano Central" e número "56", pelo RestauranteController

  Cenario: Put para atualizar Restaurante, com erro not found, pelo RestauranteController
    Dado um identificador ID de um Restaurante inexistente
    E um RestauranteDtoRequest, com nome "Fasano Atualizado", e EnderecoDtoRequest, com cep "11333-444" e logradouro "Rua Fasano Central" e número "56",e Proprietario, com email "bohr@yahoo.com"
    Quando uma requisição Put for feita no método update do RestauranteController
    Entao receber ResponseEntity com HTTP 404 do RestauranteController

  Cenario: Put para atualizar Restaurante, com erro por nome vazio, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "D.O.M"
    E um RestauranteDtoRequest, com nome "   ", e EnderecoDtoRequest, com cep "11333-444" e logradouro "Rua Fasano Central" e número "56",e Proprietario, com email "bohr@yahoo.com"
    Quando uma requisição Put for feita no método update do RestauranteController
    Entao receber ResponseEntity com HTTP 400 do RestauranteController


  Cenario: Get para consultar Restaurante, com sucesso, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "Casa do Porco"
    Quando uma requisição Get for feita no método findById do RestauranteController
    Entao receber ResponseEntity com HTTP 200 do RestauranteController
    E com RestauranteDtoResponse no body, com id e nome "Casa do Porco"
    E com EnderecoDtoResponse no body, com id e cep "78008-009" e logradouro "Rua GH" e número "1506", pelo RestauranteController

  Cenario: Get para consultar Restaurante, com erro not found, pelo RestauranteController
    Dado um identificador ID de um Restaurante inexistente
    Quando uma requisição Get for feita no método findById do RestauranteController
    Entao receber ResponseEntity com HTTP 404 do RestauranteController


  Cenario: Delete para apagar Restaurante, com sucesso, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "Coco Bambu"
    Quando uma requisição Delete for feita no método deleteById do RestauranteController
    Entao receber ResponseEntity com HTTP 204 do RestauranteController
    E o Restaurante foi apagado do banco de dados pelo RestauranteController
    E o Endereço foi apagado do banco de dados pelo RestauranteController

  Cenario: Delete para apagar Restaurante, com erro not found, pelo RestauranteController
    Dado um identificador ID de um Restaurante inexistente
    Quando uma requisição Delete for feita no método deleteById do RestauranteController
    Entao receber ResponseEntity com HTTP 404 do RestauranteController

