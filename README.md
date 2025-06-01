# PROJETO: Tech-Challenge-ADJ-Fiap

Equipe: Junior Martins (rm364241)


## Índice
1. Introdução;
2. Arquitetura do Sistema;
3. Descrição dos Endpoints da API;
4. Configuração do Projeto;
5. Qualidade do Código;
6. Collections para Teste;
7. Autoria.


## Introdução

#### Descrição do problema

Na nossa região, um grupo de restaurantes decidiu contratar estudantes
para construir um sistema de gestão para seus estabelecimentos. Essa decisão
foi motivada pelo alto custo de sistemas individuais, o que levou os restaurantes
a se unirem para desenvolver um sistema único e compartilhado. Esse sistema
permitirá que os clientes escolham restaurantes com base na comida oferecida,
em vez de se basearem na qualidade do sistema de gestão.

O objetivo é criar um sistema robusto que permita a todos os restaurantes
gerenciar eficientemente suas operações, enquanto os clientes poderão
consultar informações, deixar avaliações e fazer pedidos online. Devido à
limitação de recursos financeiros, foi acordado que a entrega do sistema será
realizada em fases, garantindo que cada etapa seja desenvolvida de forma
cuidadosa e eficaz.

A divisão em fases possibilitará uma implementação gradual e controlada,
permitindo ajustes e melhorias contínuas conforme o sistema for sendo usado
e avaliado tanto pelos restaurantes quanto pelos clientes.

#### Objetivo do projeto

Desenvolver um backend completo e robusto utilizando o framework
Spring Boot, com foco no gerenciamento de usuários, incluindo operações de
criação, atualização, exclusão e validação de login. O projeto será configurado
para rodar em um ambiente Docker, utilizando Docker Compose, o que permitirá
a orquestração dos serviços e a integração com um banco de dados relacional,
como PostgreSQL, MySQL ou H2. A configuração com Docker Compose
garantirá que a aplicação seja facilmente replicável e escalável, permitindo a
implantação em diversos ambientes de forma consistente e eficiente. Além disso,
o projeto será desenvolvido seguindo as melhores práticas de arquitetura e
segurança, de modo que o sistema seja não apenas funcional, mas também
seguro, escalável e de fácil manutenção.


## Arquitetura do Sistema

A aplicação é uma API REST para gerenciamento de usuários (Cliente e Proprietario), com operações CRUD (criar,
atualizar, consultar, deletar).

#### Descrição da Arquitetura 

Ports and Adapters (Arquitetura Hexagonal):

- A aplicação utiliza o padrão Ports and Adapters, onde as portas (UsuarioCreateInputPort, UsuarioCreateOutputPort e 
etc.) definem contratos entre camadas, e os adaptadores (UsuarioCreateAdapter, ClienteController) implementam esses 
contratos.

- Portas de Entrada: Interfaces como UsuarioCreateInputPort permitem que a camada de apresentação acesse a lógica
de negócio.

- Portas de Saída: Interfaces como UsuarioCreateOutputPort permitem que a camada de negócio acesse a persistência sem
depender de detalhes de implementação.

- Adaptadores: ClienteController (adaptador de entrada) traduz requisições HTTP em chamadas às portas de entrada,
enquanto UsuarioCreateAdapter (adaptador de saída) traduz chamadas das portas de saída em operações de banco de dados.

Clean Architecture:

- A aplicação segue princípios da Clean Architecture, com a camada de negócio (serviços) no centro, independente de
frameworks e detalhes de infraestrutura. A camada de persistência e a camada de apresentação são externas e dependem
do núcleo (negócio) por meio de interfaces.

- Exemplo: A lógica de negócio em AbstractUsuarioService não depende diretamente de Spring Data JPA ou do framework HTTP,
mas sim de interfaces abstratas (EntityMapper, UsuarioCreateOutputPort).

#### Diagrama da Arquitetura

![TechChallenge3](https://github.com/user-attachments/assets/4a9fcb71-bd00-466e-a3be-15d95ead8975)


## Descrição dos Endpoints da API e exemplos

|       Recurso       |               Endpoint                |                     Descrição                     |
|---------------------|---------------------------------------|---------------------------------------------------|
| Cliente             | /api/v1/challenge-user/clientes       | Endpoint para operações de CRUD de Clientes       |
| Proprietario        | /api/v1/challenge-user/proprietarios  | Endpoint para oeprações de CRUD de Proprietários  |


| Método |                                Endpoint / Requisição                                                                           |              Resposta                     |
|--------|--------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------|
| Delete | http://localhost:9050/api/v1/challenge-user/clientes/6d648275-37d9-4fd3-800f-025a2262ef4d                                      |           204 No Content                  |
|  Get   | http://localhost:9050/api/v1/challenge-user/clientes/a546ef31-d9f4-4ff7-9665-4baed324920b                                      |    200 OK + Json no body (resposta 1)     |
|  Post  | http://localhost:9050/api/v1/challenge-user/clientes                                           (+ Json no body - requisição 1) |  201 Created + Json no body (resposta 2)  |
|  Put   | http://localhost:9050/api/v1/challenge-user/clientes/a90902fa-7cce-4c17-87fd-5cd9c70c9d5a      (+ Json no body - requisição 1) |    200 OK + Json no body (resposta 2)     |
| Delete | http://localhost:9050/api/v1/challenge-user/proprietarios/051f5dc8-74fe-4d2c-81e2-ddea7c515532                                 |           204 No Content                  |
|  Get   | http://localhost:9050/api/v1/challenge-user/proprietarios/eb957f38-90c4-4ef2-850c-229fb1658fcd                                 |    200 OK + Json no body (resposta 3)     |
|  Post  | http://localhost:9050/api/v1/challenge-user/proprietarios                                      (+ Json no body - requisição 2) |  201 Created + Json no body (resposta 4)  |
|  Put   | http://localhost:9050/api/v1/challenge-user/proprietarios/bc11e003-219d-4884-88e9-e2a0b43d42c7 (+ Json no body - requisição 2) |    200 OK + Json no body (resposta 4)     |

##### Resposta 1 #####
```
{
    "usuarioId": "a546ef31-d9f4-4ff7-9665-4baed324920b",
    "nome": "Carl Friedrich Gauss",
    "email": "gauss@email.com",
    "login": "gauss",
    "senha": "gauss123",
    "dataHoraCriacao": "2023-10-01T12:00:00.000+00:00",
    "dataHoraEdicao": "2024-10-01T12:00:00.000+00:00",
    "endereco": {
        "enderecoId": "4f50648e-639d-423a-9a46-f4a8d1e96b07",
        "cep": "69905-169",
        "logradouro": "Travessa Nilo Bezerra",
        "numero": "500"
    },
    "numeroCartaoFidelidade": "4321-1234-001"
}
```

##### Requisição 1 #####
```
{
    "nome":"Rozenn Morgat",
    "email":"morgat@email.com",
    "login":"morgat",
    "senha":"morgat123",
    "numeroCartaoFidelidade": "1234-0000-5514",
    "endereco": {
        "cep": "78000-100",
        "logradouro": "Rua Centro",
        "numero": "100"
    }
}
```

##### Resposta 2 #####
```
{
    "usuarioId": "2827fdc3-ffac-44d9-92ce-b49680914da4",
    "nome": "Rozenn Morgat",
    "email": "morgat@email.com",
    "login": "morgat",
    "senha": "morgat123",
    "endereco": {
        "enderecoId": "ea98e788-ab70-4439-a312-7627a8d70b8b",
        "cep": "78000-100",
        "logradouro": "Rua Centro",
        "numero": "100"
    },
    "numeroCartaoFidelidade": "1234-0000-5514"
}
```

##### Resposta 3 #####
```
{
    "usuarioId": "eb957f38-90c4-4ef2-850c-229fb1658fcd",
    "nome": "Linus Pauling",
    "email": "linus@email.com",
    "login": "linus",
    "senha": "linus123",
    "dataHoraCriacao": "2023-10-01T12:00:00.000+00:00",
    "dataHoraEdicao": "2024-11-03T12:00:00.000+00:00",
    "endereco": {
        "enderecoId": "eac614d5-c70b-4b36-b4c8-7560f6f0eef9",
        "cep": "69905-169",
        "logradouro": "Rua Antônio Francisco das Chagas",
        "numero": "100"
    },
    "descricao": "Toda segunda na empresa"
}
```

##### Requisição 2 #####
```
{
    "nome": "Mike Beedle",
    "email": "mike@email.com",
    "login": "mike12",
    "senha": "123456",
    "descricao": "Presente pela tarde",
    "endereco": {
        "cep": "78050-120",
        "logradouro": "Rua Centro 2",
        "numero": "130"
    }
}
```

##### Resposta 4 #####
```
{
    "usuarioId": "0bfad517-dd66-49b3-a485-a5d6b105fac7",
    "nome": "Mike Beedle",
    "email": "mike@email.com",
    "login": "mike12",
    "senha": "123456",
    "endereco": {
        "enderecoId": "3ed4a907-ac36-4ea6-b6e7-0d17ba24d7a9",
        "cep": "78050-120",
        "logradouro": "Rua Centro 2",
        "numero": "130"
    },
    "descricao": "Presente pela tarde"
}
```


Mais informações podem ser adquiridas via Swagger (rode o docker compose): http://localhost:9050/swagger-ui/index.html


## Configuração do Projeto

#### Configuração do Docker Compose

```
name: fiap_technical_challenge_adj

volumes:
  database_user:
    name: database_user

networks:
  net_applications: # conexão com infra-local e acesso a internet - tipo bridge
    name: net_applications
    driver: bridge

services:

  challenge_user:
    container_name: challenge_user
    image: juniorsmartins/challenge_user:v0.0.4
    build:
      context: ../challenge_user/
      dockerfile: Dockerfile
      args:
        APP_NAME: "challenge_user"
        APP_VERSION: "v0.0.4"
        APP_DESCRIPTION: "Serviço de Crud de usuários."
    ports:
      - "9050:9050"
    deploy:
      resources:
        limits:
          cpus: '1.0'
          memory: 512M
    environment:
      - DB_HOST=challenge_data_user
      - DB_NAME=challenge_user
      - DB_PORT=5432
    restart: on-failure
    networks:
      - net_applications
    depends_on:
      challenge_data_user:
        condition: service_started

  challenge_data_user:
    container_name: challenge_data_user
    image: postgres:16.0
    ports:
      - "5501:5432"
    deploy:
      resources:
        limits:
          cpus: '0.5'
          memory: 512M
    restart: on-failure
    environment:
      - POSTGRES_DB=challenge_user
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
    volumes:
      - database_user:/var/lib/postgresql/data
    networks:
      - net_applications
```

#### Instruções para execução local

Passo 1: clone o projeto;
Passo 2: abra o projeto na IDEA;
Passo 3: abra o terminal da IDEA;
Passo 4: entre no diretório docker;
Passo 5: rode o comando no diretório docker: docker compose up --build -d


## Qualidade do Código

#### Boas Práticas Utilizadas

##### SOLID
- Princípio da Responsabilidade Única (SRP):

O Princípio da Responsabilidade Única (Single Responsibility Principle) estabelece que uma classe deve ter apenas
um motivo para mudar, ou seja, deve ser responsável por uma única parte da funcionalidade do sistema, e essa
responsabilidade deve ser totalmente encapsulada pela classe. Isso reduz o acoplamento, facilita a manutenção e
melhora a legibilidade do código, já que mudanças em uma responsabilidade específica afetam apenas uma classe.

1. Separação de Camadas

A aplicação segue uma arquitetura em camadas, com responsabilidades bem definidas:

Controllers (AbstractUsuarioController, ClienteController, ProprietarioController): A classe abstrata
AbstractUsuarioController lida com operações CRUD (criar, atualizar, buscar, deletar) de forma genérica, enquanto
as subclasses (ClienteController, ProprietarioController) definem endpoints específicos. Isso é uma boa separação,
pois o controlador genérico centraliza a lógica comum, e as subclasses configuram o contexto específico. Cada
controlador tem a responsabilidade clara de gerenciar requisições HTTP.

Serviços (AbstractUsuarioService, ClienteService, ProprietarioService): Contêm a lógica de negócio para operações
como criação, atualização e exclusão de usuários. A classe abstrata AbstractUsuarioService lida com operações de forma
genérica,enquanto as subclasses definem contextos específicos. Dessa forma, a subclasse ClienteService é responsável por
operações de Cliente e a subclasse ProprietarioService é responsável por operações de Proprietario. Ou seja, cada
uma possui um único motivo para mudar. Elas mudarão apenas se mudarem as regras por seu tipo específico de usuário.

Adaptadores (UsuarioCreateAdapter, UsuarioDeleteAdapter, UsuarioFindByIdAdapter): Lidam com a persistência de dados,
interagindo com o repositório JPA. Cada adaptador lida com uma única operação de acesso a dados (salvar, deletar, buscar),
alinhando-se ao SRP.

Regras de Atualização (DefaultUsuarioUpdateRule, DefaultEnderecoUpdateRule): A segregação das regras de atualização
em UsuarioUpdateRule (para dados do usuário) e EnderecoUpdateRule (para endereços) demonstra uma clara separação de
responsabilidades, com cada classe focada em uma tarefa específica.

Essa separação garante que cada camada tenha uma única responsabilidade, facilitando a manutenção e a escalabilidade.

2. Mapeadores Específicos

Mapeadores (ClienteMapper, ProprietarioMapper, EnderecoMapper): Responsáveis por transformar dados entre DTOs, objetos de
domínio e entidades de persistência. As classes ClienteMapper e ProprietarioMapper implementam métodos com
responsabilidades únicas, por exemplo:

toDomainIn: Converte DTOs de entrada em objetos de domínio.
toEntity: Converte objetos de domínio em entidades de persistência.
toDtoResponse: Converte objetos de domínio ou entidades em DTOs de saída.

Cada método é focado em uma única tarefa, respeitando o SRP.

3. Interfaces Específicas

Interfaces como UsuarioCreateInputPort, UsuarioUpdateInputPort e UsuarioDeleteByIdInputPort definem contratos claros
para operações específicas, garantindo que cada interface tenha uma única responsabilidade.

4. Entidades

As classes ClienteEntity e ProprietarioEntity são responsáveis apenas por representar os dados persistidos, sem conter
lógica de negócio, o que está alinhado com o SRP.


- Princípio Aberto/Fechado (OCP):

O Princípio Aberto/Fechado (Open/Closed Principle) estabelece que as entidades de software (classes, módulos, funções e
etc.) devem estar abertas para extensão, mas fechadas para modificação. Novas funcionalidades são adicionadas por meio
de extensões (como subclasses ou implementações), sem alterar o código existente. Isso significa que o comportamento de
uma classe pode ser estendido para atender a novos requisitos sem alterar seu código-fonte existente. O OCP promove a
flexibilidade e a reutilização do código, reduzindo o risco de introduzir erros ao modificar classes já testadas.

1. Uso de Classes Abstratas:

Classes Abstratas (AbstractUsuarioController, AbstractUsuarioService): O uso de generics (T, E) permite
adicionar novos tipos de usuários (como Admin ou Funcionario) criando subclasses (AdminController e
AdminService ou FuncionarioController e FuncionarioService) sem modificar as classes base.

Exemplo: O método create em AbstractUsuarioController é reutilizado por ClienteController e ProprietarioController sem
alterações, permitindo que novos tipos de usuários sejam adicionados por meio de novas subclasses.

2. Uso de Interfaces Genéricas:

Interfaces Genéricas (UsuarioCreateInputPort, UsuarioUpdateInputPort e etc.): Interfaces como UsuarioCreateInputPort<T>,
UsuarioUpdateInputPort<T>, UsuarioDeleteByIdInputPort<T>, InputMapper<I, U, T>, e OutputMapper<T, O, E> são genéricas,
permitindo que diferentes tipos de usuários (Cliente, Proprietario) sejam manipulados sem alterar o contrato definido
pelas interfaces. Novos tipos de usuários podem implementar essas interfaces sem alterar o código existente.

Exemplo: A interface UsuarioCreateInputPort<T> define o método create(T usuario), que é implementado por ClienteService e
ProprietarioService. Isso permite adicionar suporte a novos tipos de usuários (por exemplo, Administrador) criando novas
implementações da interface, sem modificar o código existente.

Regras de Atualização (UsuarioUpdateRule, EnderecoUpdateRule): As interfaces permitem novas implementações para
diferentes tipos de usuários ou regras específicas, sem modificar as classes base como DefaultUsuarioUpdateRule e
DefaultEnderecoUpdateRule.

3. Mapeadores Genéricos:

As interfaces InputMapper, OutputMapper e EntityMapper são projetadas para suportar diferentes tipos de entrada (I, U),
saída (O) e entidades (T, E). Isso torna os mapeadores extensíveis para novos tipos de DTOs ou entidades sem alterar suas
implementações existentes.

Exemplo: ClienteMapper e ProprietarioMapper implementam essas interfaces, permitindo que novos mapeadores sejam criados para
outras entidades (como Fornecedor) sem modificar as interfaces.

4. Uso de Herança na Camada de Persistência:

A classe abstrata UsuarioEntity define atributos e comportamentos comuns (como nome, email, login, senha, e endereco),
enquanto ClienteEntity e ProprietarioEntity estendem essa classe para adicionar campos específicos (numeroCartaoFidelidade
para ClienteEntity e descricao para ProprietarioEntity). Isso permite a extensão para novos tipos de entidades sem alterar
UsuarioEntity.

Exemplo: A adição de uma nova entidade, como AdministradorEntity, pode ser feita criando uma nova classe que estende
UsuarioEntity, sem modificar o código existente.


- Princípio da Substituição de Liskov (LSP):

O Princípio de Substituição de Liskov (Liskov Substitution Principle) estabelece que objetos de uma classe derivada devem poder
substituir objetos da classe base sem alterar o comportamento correto do programa. Em outras palavras, uma subclasse deve ser
substituível por sua superclasse sem quebrar as expectativas do código que utiliza a superclasse. Isso implica que as subclasses
devem respeitar os contratos definidos pela superclasse, incluindo pré-condições, pós-condições e invariantes.

1. Herança de AbstractUsuarioController:

As classes ClienteController e ProprietarioController estendem AbstractUsuarioController, que define métodos genéricos para
operações CRUD (create, update, findById, deleteById). Essas subclasses utilizam a implementação genérica da superclasse, passando
tipos específicos (ClienteDtoRequest, ClienteDtoResponse, etc.) via parâmetros genéricos.

Conformidade: As subclasses respeitam o contrato da superclasse, pois utilizam os métodos herdados sem alterar seu comportamento.
Por exemplo, o método create em ClienteController funciona da mesma forma que em AbstractUsuarioController, apenas com tipos
específicos (ClienteDtoRequest, Cliente, etc.), mantendo as pré-condições (entrada válida) e pós-condições (retorno de um
ResponseEntity com status 201).

2. Herança de AbstractUsuarioService:

As classes ClienteService e ProprietarioService estendem AbstractUsuarioService e implementam interfaces específicas
(UsuarioCreateInputPort, UsuarioUpdateInputPort, UsuarioDeleteByIdInputPort). A superclasse define a lógica genérica para operações
de negócio, enquanto as subclasses apenas delegam para os métodos herdados.

Conformidade: As subclasses não alteram o comportamento dos métodos herdados (create, update, deleteById), garantindo que qualquer
código que utilize AbstractUsuarioService possa substituir por ClienteService ou ProprietarioService sem quebrar o sistema.

Exemplo: O método create em ClienteService chama super.create, preservando as pré-condições (entrada não nula) e pós-condições
(retorno de um objeto do tipo Cliente).

3. Herança de UsuarioEntity:

As classes ClienteEntity e ProprietarioEntity estendem UsuarioEntity, que define atributos comuns (usuarioId, nome, email, login,
senha, endereco). Cada subclasse adiciona atributos específicos (numeroCartaoFidelidade para ClienteEntity e descricao para
ProprietarioEntity), mas não altera o comportamento ou os contratos da superclasse.

Conformidade: Como UsuarioEntity é uma classe de modelo de dados sem métodos complexos, as subclasses mantêm a compatibilidade com a
superclasse. Por exemplo, o repositório UsuarioRepository pode manipular instâncias de ClienteEntity ou ProprietarioEntity sem problemas,
já que ambas respeitam a estrutura definida por UsuarioEntity.

Benefício: Qualquer operação que utilize UsuarioEntity (como buscas no repositório) funciona corretamente com ClienteEntity ou
ProprietarioEntity.

4. Interfaces Genéricas:

Interfaces como UsuarioCreateInputPort<T>, UsuarioUpdateInputPort<T>, e UsuarioDeleteByIdInputPort<T> são implementadas por
ClienteService e ProprietarioService com tipos específicos (Cliente e Proprietario). Essas implementações respeitam os contratos das
interfaces, garantindo que métodos como create(T usuario) ou deleteById(UUID id) funcionem como esperado.

Conformidade: As implementações específicas não introduzem comportamentos inesperados, permitindo que qualquer código que dependa dessas
interfaces utilize ClienteService ou ProprietarioService de forma intercambiável.


- Princípio da Segregação de Interfaces (ISP):

O Princípio de Segregação de Interfaces (Interface Segregation Principle) estabelece que os clientes não devem ser forçados a depender
de interfaces que não utilizam. Em outras palavras, uma classe não deve ser obrigada a implementar métodos que não são relevantes para
sua funcionalidade. Interfaces devem ser específicas e coesas, contendo apenas os métodos necessários para um contexto específico,
reduzindo o acoplamento e facilitando a manutenção. Muitas interfaces específicas são melhores que uma interface geral.

1. Interfaces Específicas para Portas:

A aplicação define interfaces específicas para diferentes operações de negócio, como UsuarioCreateInputPort<T>, UsuarioUpdateInputPort<T>,
UsuarioDeleteByIdInputPort<T>, e UsuarioFindByIdOutputPort<E>. Cada interface contém apenas um método relacionado à sua
responsabilidade (por exemplo, create para criação, update para atualização, deleteById para exclusão).

Conformidade: Essas interfaces são coesas e focadas em uma única operação, garantindo que as classes que as implementam (como
ClienteService e ProprietarioService) não sejam forçadas a implementar métodos desnecessários.

Exemplo: A interface UsuarioCreateInputPort<T> define apenas o método T create(T usuario), e ClienteService implementa apenas esse
método para criação de clientes, sem precisar de métodos irrelevantes como exclusão ou busca.

2. Interfaces de Repositório:

Os adaptadores de repositório (UsuarioCreateAdapter, UsuarioDeleteAdapter, UsuarioFindByIdAdapter) implementam interfaces específicas
(UsuarioCreateOutputPort<E>, UsuarioDeleteOutputPort<E>, UsuarioFindByIdOutputPort<E>), cada uma com um único método correspondente à
sua função (salvar, excluir, buscar por ID).

Conformidade: Cada adaptador implementa apenas a interface necessária para sua operação, evitando a inclusão de métodos desnecessários.

Benefício: Isso reduz o acoplamento e garante que os adaptadores sejam usados apenas para as operações que suportam.

3. Uso de Generics:

As interfaces genéricas (UsuarioCreateInputPort<T>, InputMapper<I, U, T>, etc.) permitem que diferentes tipos de entidades (Cliente e
Proprietario) sejam manipulados sem forçar a implementação de métodos irrelevantes. Por exemplo, ClienteService implementa
UsuarioCreateInputPort<Cliente>, que é específico para o tipo Cliente.

Conformidade: A flexibilidade dos generics garante que as interfaces sejam aplicadas apenas aos tipos relevantes, mantendo a coesão.


- Princípio da Inversão de Dependência (DIP):

O Princípio de Inversão de Dependência (Dependency Inversion Principle) estabelece que: Módulos de alto nível não devem depender de
módulos de baixo nível; ambos devem depender de abstrações. Abstrações não devem depender de detalhes; detalhes devem depender de
abstrações.

Isso significa que classes de alto nível (como controllers e serviços) devem interagir com dependências por meio de interfaces ou
classes abstratas, em vez de classes concretas. Além disso, as implementações concretas devem depender de interfaces, promovendo baixo
acoplamento, maior flexibilidade e facilidade de substituição de componentes.

1. Uso de Interfaces para Portas:

A aplicação utiliza interfaces como UsuarioCreateInputPort<T>, UsuarioUpdateInputPort<T>, UsuarioDeleteByIdInputPort<T>, e
UsuarioFindByIdOutputPort<E> para definir contratos entre camadas. Essas interfaces são injetadas em classes de alto nível, como
AbstractUsuarioController, que dependem dessas abstrações em vez de implementações concretas.

Conformidade: AbstractUsuarioController depende de interfaces como UsuarioCreateInputPort<T> e OutputMapper<T, O, E>, enquanto as
implementações concretas (ClienteService, ProprietarioService, ClienteMapper, ProprietarioMapper) são injetadas via injeção de
dependências. Isso respeita o DIP, pois o controller (módulo de alto nível) não depende diretamente de classes concretas.

Exemplo: No método create de AbstractUsuarioController, a lógica utiliza createInputPort.create e outputMapper.toDtoResponse, sem
conhecer as implementações específicas (ClienteService ou ClienteMapper).

2. Injeção de Dependências com Spring:

A aplicação utiliza o framework Spring com anotações como @RequiredArgsConstructor (Lombok) para injetar dependências por meio de
interfaces, garantindo que classes de alto nível dependam apenas de abstrações.

Conformidade: Em AbstractUsuarioService, dependências como EntityMapper<T, E>, UsuarioCreateOutputPort<E>, e UsuarioFindByIdOutputPort<E>
são injetadas como interfaces, permitindo que implementações concretas (como ClienteMapper ou UsuarioCreateAdapter) sejam fornecidas pelo
contêiner do Spring sem acoplamento direto.

Benefício: Isso facilita a substituição de implementações (por exemplo, trocar um adaptador de banco de dados por um mock em testes)
sem alterar o código das classes consumidoras.

3. Interfaces de Mapeamento:

As interfaces InputMapper<I, U, T>, OutputMapper<T, O, E>, e EntityMapper<T, E> são usadas para definir contratos de mapeamento, e
classes como ClienteMapper e ProprietarioMapper implementam essas abstrações.

Conformidade: Classes de alto nível, como AbstractUsuarioController e AbstractUsuarioService, dependem dessas interfaces, enquanto as
implementações concretas são injetadas. Isso segue o DIP, pois os detalhes (mapeadores concretos) dependem das abstrações
(interfaces de mapeamento).

4. Adaptadores de Repositório:

Os adaptadores (UsuarioCreateAdapter, UsuarioDeleteAdapter, UsuarioFindByIdAdapter) implementam interfaces específicas
(UsuarioCreateOutputPort<E>, UsuarioDeleteOutputPort<E>, UsuarioFindByIdOutputPort<E>), que são injetadas em AbstractUsuarioService. O
repositório concreto (UsuarioRepository) é encapsulado pelos adaptadores, que dependem da abstração fornecida pelo Spring Data JPA
(JpaRepository).

Conformidade: A camada de serviço depende de interfaces de saída (UsuarioCreateOutputPort, etc.), enquanto os adaptadores dependem da
interface UsuarioRepository, respeitando o DIP.


##### TDD

TDD: Prática ágil onde testes são escritos antes do código, seguindo o ciclo Red-Green-Refactor.

Cucumber: Ferramenta de Behavior-Driven Development (BDD) capaz de escrever especificações em linguagem natural (Gherkin) mapeadas para
testes automatizados.
- Via Gradle, foi criado um módulo, chamado acceptanceTest, para organizar os testes de aceitação;
- Nesse módulo, foram escritos cenários em arquivos .feature para descrever o comportamento dos endpoints;
- E passos Gherkin foram implementados em step definitions Java, interagindo com a API via RestAssured.

Motivos para Considerar Boa Prática

Colaboração: Gherkin permite que stakeholders não técnicos validem requisitos, promovendo uma linguagem ubíqua.
Documentação Viva: Cenários .feature documentam o comportamento da API, úteis para onboarding e auditorias.
Foco no Comportamento: Testes validam resultados visíveis (ex.: status 201), não detalhes internos.
Integração com TDD: Testes de aceitação guiam o desenvolvimento iterativo.
Manutenibilidade: Step definitions reutilizáveis e testes alinhados com a Arquitetura Hexagonal.


## Collections para Teste

#### Link para a Collection do Postman

[Link para baixar coleção do Postman - Clique aqui](postman/TechChallenge-ADJ.postman_collection.json)  

#### Descrição dos Testes Manuais

Você precisa subir a aplicação com o comando docker compose up --build -d (mais informações na sessão "Instruções para execução local") e
depois baixar a coleção do Postman, importá-la no Postman e dar send nas requisições e ver a resposta. Todas as requisições estão prontas
para execução. A aplicação possui um arquivo, chamado import.sql, responsável por gerar pequena massa de dados para os testes em questão.


## Repositório do Código 

[Link para o repositório do código](https://github.com/juniorsmartins/fiap_technical_challenge_adj)


## Autoria

[Junior Martins](https://www.linkedin.com/in/juniorsmartins/)
