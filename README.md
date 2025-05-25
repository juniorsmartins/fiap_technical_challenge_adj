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

Mapeadores (ClienteMapper, ProprietarioMapper, EnderecoMapper): Responsáveis por transformar dados entre DTOs,
objetos de domínio e entidades de persistência.

Essa separação garante que cada camada tenha uma única responsabilidade, facilitando a manutenção e a escalabilidade.

2. Mapeadores Específicos

As classes ClienteMapper e ProprietarioMapper implementam métodos com responsabilidades únicas, por exemplo:

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


Exemplo:


O mesmo padrão ocorre nos serviços. 

Adaptadores (UsuarioCreateAdapter, UsuarioDeleteAdapter, UsuarioFindByIdAdapter): Cada adaptador lida com uma única
operação de acesso a dados (salvar, deletar, buscar), alinhando-se ao SRP.

Regras de Atualização (DefaultUsuarioUpdateRule, DefaultEnderecoUpdateRule): A segregação das regras de atualização
em UsuarioUpdateRule (para dados do usuário) e EnderecoUpdateRule (para endereços) demonstra uma clara separação de
responsabilidades, com cada classe focada em uma tarefa específica.

```
- Princípio Aberto/Fechado (OCP):
```
Explicação:

O Princípio Aberto/Fechado determina que as classes devem estar abertas para extensão, mas fechadas para modificação.
Novas funcionalidades são adicionadas por meio de extensões (como subclasses ou implementações), sem alterar o
código existente.

Exemplo:

Classes Abstratas (AbstractUsuarioController, AbstractUsuarioService): O uso de generics (T, E) permite
adicionar novos tipos de usuários (como Admin ou Funcionario) criando subclasses (AdminController e
AdminService ou FuncionarioController e FuncionarioService) sem modificar as classes base.

Interfaces Genéricas (UsuarioCreateInputPort, UsuarioUpdateInputPort e etc.): Novos tipos de usuários podem
implementar essas interfaces sem alterar o código existente.

Regras de Atualização (UsuarioUpdateRule, EnderecoUpdateRule): As interfaces permitem novas implementações para
diferentes tipos de usuários ou regras específicas, sem modificar as classes base como DefaultUsuarioUpdateRule e
DefaultEnderecoUpdateRule.

Adaptadores (UsuarioCreateAdapter, UsuarioDeleteAdapter, UsuarioFindByIdAdapter): Usam generics para suportar
diferentes tipos de entidades (ClienteEntity, ProprietarioEntity), permitindo extensão para novos tipos.

```
- Princípio da Substituição de Liskov (LSP):
```
Explicação:

O Princípio da Substituição de Liskov estabelece que uma subclasse deve ser substituível por sua superclasse sem
alterar o comportamento do programa. Isso garante que polimorfismo funcione corretamente.

Exemplo:

Subclasses de AbstractUsuarioController: ClienteController e ProprietarioController herdam de
AbstractUsuarioController e configuram URIs, mantendo o comportamento dos métodos CRUD. São substituíveis
pela classe base.

Subclasses de AbstractUsuarioService: ClienteService e ProprietarioService delegam para a superclasse,
garantindo comportamento consistente.

Entidades e Modelos: ClienteEntity e ProprietarioEntity herdam de UsuarioEntity, e Cliente e Proprietario herdam
de Usuario. Os métodos genéricos usam tipos parametrizados (T, E), garantindo substituição.

```
- Princípio da Segregação de Interfaces (ISP):
```
Explicação:

O Princípio da Segregação de Interfaces determina que clientes não devem ser forçados a depender de interfaces que não
utilizam. Interfaces devem ser específicas e focadas. Muitas interfaces específicas são melhores que uma interface geral.
Clientes não devem depender de interfaces que não usam.

Exemplo:

Interfaces Específicas (UsuarioCreateInputPort, UsuarioUpdateInputPort, etc.): Cada interface define uma única
operação (create, update, delete, findById), permitindo que os clientes (como AbstractUsuarioController) dependam
apenas do necessário.

Adaptadores (UsuarioCreateAdapter, UsuarioDeleteAdapter, etc.): Cada adaptador implementa uma interface
específica (UsuarioCreateOutputPort, UsuarioDeleteOutputPort, etc.), garantindo dependências mínimas.

Regras de Atualização (UsuarioUpdateRule, EnderecoUpdateRule): Interfaces segregadas garantem que implementações lidem com
uma única responsabilidade (atualizar usuário ou endereço).

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
