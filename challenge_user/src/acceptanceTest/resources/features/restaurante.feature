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
      |         nome        | tipoCozinhaEnum |  horaAbertura  |  horaFechamento  |      cep     |  logradouro  |   numero   |     proprietario      |
      |    Casa do Porco    |    CARNIVORA    |    08:00:00    |     12:00:00     |   78008-009  |    Rua GH    |    1506    |   hawking@yahoo.com   |
      |      Coco Bambu     |     CHINESA     |    09:00:00    |     22:00:00     |   78511-876  |    Rua BN    |    2234    |  socrates@yahoo.com   |
      |        Fasano       |      TURCA      |    08:30:00    |     20:30:00     |   78689-123  |    Rua MS    |    3349    |   platao@yahoo.com    |
      |        D.O.M        |     VEGANA      |    10:00:00    |     18:00:00     |   78543-400  |    Rua DG    |    1201    | aristoteles@yahoo.com |

  Cenario: Post para criar Restaurante e Endereço, com sucesso, pelo RestauranteController
    Dado um RestauranteDtoRequest, com nome "Arturito" e tipoCozinhaEnum "MEXICANA" e horaAbertura "07:30:30" e horaFechamento "18:30:30", e EnderecoDtoRequest, com cep "25444-222" e logradouro "Rua Avelino Popo" e número "550",e Proprietario, com email "galilei@yahoo.com"
    Quando a requisição Post for feita no método create do RestauranteController
    Entao receber ResponseEntity com HTTP 201 do RestauranteController
    E com RestauranteDtoResponse no body, com id e nome "Arturito" e tipoCozinhaEnum "MEXICANA" e horaAbertura "07:30:30" e horaFechamento "18:30:30"
    E com EnderecoDtoResponse no body, com id e cep "25444-222" e logradouro "Rua Avelino Popo" e número "550", pelo RestauranteController
    E com Proprietario no body, com email "galilei@yahoo.com"
    E o Restaurante cadastrado no banco de dados possui nome "Arturito" e tipoCozinhaEnum "MEXICANA" e horaAbertura "07:30:30" e horaFechamento "18:30:30"
    E um Endereço salvo no database, com cep "25444-222" e logradouro "Rua Avelino Popo" e número "550", pelo RestauranteController

  Cenario: Post para criar Restaurante e Endereço, com erro por Proprietario not found, pelo RestauranteController
    Dado um RestauranteDtoRequest, com nome "Casa das Aves" e tipoCozinhaEnum "MEXICANA" e horaAbertura "11:30:30" e horaFechamento "17:00:30", e EnderecoDtoRequest, com cep "90788-500" e logradouro "Rua Aves" e número "400",e Proprietario, com Id inexistente
    Quando a requisição Post for feita no método create do RestauranteController
    Entao receber ResponseEntity com HTTP 404 do RestauranteController

  Cenario: Post para criar Restaurante e Endereço, com erro por Id de Cliente como Proprietario, pelo RestauranteController
    Dado um RestauranteDtoRequest, com nome "Churrascaria do Boi" e tipoCozinhaEnum "MEXICANA" e horaAbertura "11:10:10" e horaFechamento "17:10:10", e EnderecoDtoRequest, com cep "78000-550" e logradouro "Rua do Boi" e número "400",e Proprietario, com email "newton@email.com"
    Quando a requisição Post for feita no método create do RestauranteController
    Entao receber ResponseEntity com HTTP 404 do RestauranteController

  Cenario: Post para criar Restaurante e Endereço, com erro por nome vazio, pelo RestauranteController
    Dado um RestauranteDtoRequest, com nome "   " e tipoCozinhaEnum "MEXICANA" e horaAbertura "11:20:20" e horaFechamento "17:20:20", e EnderecoDtoRequest, com cep "25444-222" e logradouro "Rua Avelino Popo" e número "550",e Proprietario, com email "galilei@yahoo.com"
    Quando a requisição Post for feita no método create do RestauranteController
    Entao receber ResponseEntity com HTTP 400 do RestauranteController

  Cenario: Post para criar Restaurante e Endereço, com erro por horaAbertura posterior horaFechamento, pelo RestauranteController
    Dado um RestauranteDtoRequest, com nome "Churros de Mel" e tipoCozinhaEnum "MEXICANA" e horaAbertura "18:20:20" e horaFechamento "08:20:20", e EnderecoDtoRequest, com cep "25444-222" e logradouro "Rua Avelino Popo" e número "550",e Proprietario, com email "galilei@yahoo.com"
    Quando a requisição Post for feita no método create do RestauranteController
    Entao receber ResponseEntity com HTTP 409 do RestauranteController


  Cenario: Put para atualizar Restaurante e Endereço, com sucesso, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "Fasano"
    E um RestauranteDtoRequest, com nome "Fasano Atualizado" e tipoCozinhaEnum "CHILENA" e horaAbertura "10:40:40" e horaFechamento "18:40:40", e EnderecoDtoRequest, com cep "11333-444" e logradouro "Rua Fasano Central" e número "56",e Proprietario, com email "bohr@yahoo.com"
    Quando uma requisição Put for feita no método update do RestauranteController
    Entao receber ResponseEntity com HTTP 200 do RestauranteController
    E com RestauranteDtoResponse no body, com id e nome "Fasano Atualizado" e tipoCozinhaEnum "CHILENA" e horaAbertura "10:40:40" e horaFechamento "18:40:40"
    E com EnderecoDtoResponse no body, com id e cep "11333-444" e logradouro "Rua Fasano Central" e número "56", pelo RestauranteController
    E o Restaurante cadastrado no banco de dados possui nome "Fasano Atualizado" e tipoCozinhaEnum "CHILENA" e horaAbertura "10:40:40" e horaFechamento "18:40:40"
    E um Endereço salvo no database, com cep "11333-444" e logradouro "Rua Fasano Central" e número "56", pelo RestauranteController

  Cenario: Put para atualizar Proprietario do Restaurante e Endereço, com sucesso, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "Fasano"
    E um RestauranteDtoRequest, com nome "Fasano Atualizado" e tipoCozinhaEnum "CHILENA" e horaAbertura "10:40:40" e horaFechamento "18:40:40", e EnderecoDtoRequest, com cep "11333-444" e logradouro "Rua Fasano Central" e número "56",e Proprietario, com email "bohr@yahoo.com"
    Quando uma requisição Put for feita no método update do RestauranteController
    Entao receber ResponseEntity com HTTP 200 do RestauranteController
    E com RestauranteDtoResponse no body, com id e nome "Fasano Atualizado" e tipoCozinhaEnum "CHILENA" e horaAbertura "10:40:40" e horaFechamento "18:40:40"
    E com EnderecoDtoResponse no body, com id e cep "11333-444" e logradouro "Rua Fasano Central" e número "56", pelo RestauranteController
    E com Proprietario no body, com email "bohr@yahoo.com"
    E o Restaurante cadastrado no banco de dados possui nome "Fasano Atualizado" e tipoCozinhaEnum "CHILENA" e horaAbertura "10:40:40" e horaFechamento "18:40:40"
    E um Endereço salvo no database, com cep "11333-444" e logradouro "Rua Fasano Central" e número "56", pelo RestauranteController

  Cenario: Put para atualizar Restaurante e Endereço, com erro por Id de Cliente como Proprietario, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "Fasano"
    E um RestauranteDtoRequest, com nome "Fasano Bovino" e tipoCozinhaEnum "CHILENA" e horaAbertura "10:55:55" e horaFechamento "18:55:55", e EnderecoDtoRequest, com cep "78000-550" e logradouro "Rua do Boi" e número "400",e Proprietario, com email "newton@email.com"
    Quando uma requisição Put for feita no método update do RestauranteController
    Entao receber ResponseEntity com HTTP 404 do RestauranteController

  Cenario: Put para atualizar Restaurante, com erro not found, pelo RestauranteController
    Dado um identificador ID de um Restaurante inexistente
    E um RestauranteDtoRequest, com nome "Fasano Atualizado" e tipoCozinhaEnum "CHILENA" e horaAbertura "10:55:55" e horaFechamento "18:55:55", e EnderecoDtoRequest, com cep "11333-444" e logradouro "Rua Fasano Central" e número "56",e Proprietario, com email "bohr@yahoo.com"
    Quando uma requisição Put for feita no método update do RestauranteController
    Entao receber ResponseEntity com HTTP 404 do RestauranteController

  Cenario: Put para atualizar Restaurante, com erro por nome vazio, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "D.O.M"
    E um RestauranteDtoRequest, com nome "   " e tipoCozinhaEnum "CHILENA" e horaAbertura "10:55:55" e horaFechamento "18:55:55", e EnderecoDtoRequest, com cep "11333-444" e logradouro "Rua Fasano Central" e número "56",e Proprietario, com email "bohr@yahoo.com"
    Quando uma requisição Put for feita no método update do RestauranteController
    Entao receber ResponseEntity com HTTP 400 do RestauranteController

  Cenario: Put para atualizar Restaurante, com erro por horaAbertura posterior horaFechamento, pelo RestauranteController
    Dado um identificador ID de um Restaurante inexistente
    E um RestauranteDtoRequest, com nome "Fasano Atualizado" e tipoCozinhaEnum "CHILENA" e horaAbertura "21:55:55" e horaFechamento "10:55:55", e EnderecoDtoRequest, com cep "11333-444" e logradouro "Rua Fasano Central" e número "56",e Proprietario, com email "bohr@yahoo.com"
    Quando uma requisição Put for feita no método update do RestauranteController
    Entao receber ResponseEntity com HTTP 409 do RestauranteController


  Cenario: Get para consultar Restaurante, com sucesso, pelo RestauranteController
    Dado um identificador ID de um Restaurante existente, com nome "Casa do Porco"
    Quando uma requisição Get for feita no método findById do RestauranteController
    Entao receber ResponseEntity com HTTP 200 do RestauranteController
    E com RestauranteDtoResponse no body, com id e nome "Casa do Porco" e tipoCozinhaEnum "CARNIVORA" e horaAbertura "08:00:00" e horaFechamento "12:00:00"
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

