# PROJETO: Tech-Challenge-ADJ-Fiap


## Índice
1. Introdução;
2. Arquitetura do Sistema;
3. Descrição dos Endpoints da API;
4. Configuração do Projeto;
5. Qualidade do Código;
6. Collections para Teste;
7. Notas.


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
permitindo ajustes e melhorias contínuas conforme o sistema for sendo utilizado
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

#### Descrição da Arquitetura 

#### Diagrama da Arquitetura


## Descrição dos Endpoints da API

#### Tabela de Endpoints

#### Exemplos de requisição e resposta


## Configuração do Projeto

#### Configuração do Docker Compose

#### Instruções para execução local


## Qualidade do Código

#### Boas Práticas Utilizadas

##### SOLID
- Princípio da Responsabilidade Única (SRP):
```
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


```
- Princípio Aberto/Fechado (OCP):
```

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


```
- Princípio da Substituição de Liskov (LSP):
```

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


```
- Princípio da Segregação de Interfaces (ISP):
```

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


```
- Princípio da Inversão de Dependência (DIP):
```


Explicação:

O Princípio da Inversão de Dependência estabelece que módulos de alto nível não devem depender de módulos de baixo nível;
ambos devem depender de abstrações. Abstrações não devem depender de detalhes.

Exemplo:

Injeção de Dependências: O uso de @RequiredArgsConstructor para injetar interfaces (UsuarioCreateInputPort,
UsuarioCreateOutputPort, AbstractUsuarioMapper, etc.) é uma boa prática. Controladores e serviços dependem de
abstrações.

Interfaces Genéricas: Interfaces como UsuarioCreateInputPort<T> e UsuarioCreateOutputPort<E> permitem que o código
de alto nível use tipos genéricos, enquanto implementações específicas fornecem os detalhes.

```


## Collections para Teste

#### Link para a Collection do Postman

#### Descrição dos Testes Manuais


## Notas
