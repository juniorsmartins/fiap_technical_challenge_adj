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
Explicação: Uma classe deve ter apenas um motivo para mudar, ou seja, deve ter apenas uma responsabilidade.

```
- Princípio Aberto/Fechado (OCP):
```
Explicação:

As classes devem estar abertas para extensão, mas fechadas para modificação. Novas funcionalidades
devem ser adicionadas por meio de extensões, sem alterar o código existente.

Classes Abstratas (AbstractUsuarioController, AbstractUsuarioService): O uso de generics (T, E) permite
adicionar novos tipos de usuários (como Admin ou Funcionario) criando subclasses (AdminController e
AdminService ou FuncionarioController e FuncionarioService) sem modificar as classes base.

Interfaces (UsuarioCreateInputPort, UsuarioUpdateInputPort e etc.): Novos tipos de usuários podem implementar
essas interfaces sem alterar o código existente.

Exemplo:

Controladores (AbstractUsuarioController, ClienteController, ProprietarioController): A classe abstrata
AbstractUsuarioController lida com operações CRUD (criar, atualizar, buscar, deletar) de forma genérica, enquanto
as subclasses (ClienteController, ProprietarioController) definem endpoints específicos. Isso é uma boa separação,
pois o controlador genérico centraliza a lógica comum, e as subclasses configuram o contexto específico. Cada
controlador tem a responsabilidade clara de gerenciar requisições HTTP.
```
- Princípio da Substituição de Liskov (LSP):
```
Explicação: Objetos de uma superclasse devem poder ser substituídos por objetos de uma subclasse sem quebrar a
aplicação.

Subclasses de AbstractUsuarioController: ClienteController e ProprietarioController herdam de
AbstractUsuarioController e apenas configuram URIs, mantendo o comportamento dos métodos CRUD. São substituíveis
pela classe base.

Subclasses de AbstractUsuarioService: ClienteService e ProprietarioService delegam para a superclasse,
garantindo comportamento consistente.

Entidades e Modelos: ClienteEntity e ProprietarioEntity herdam de UsuarioEntity, e Cliente e Proprietario herdam
de Usuario. Os métodos genéricos usam tipos parametrizados (T, E), garantindo substituição.

AbstractUsuarioUtils: A classe é genérica e funciona com qualquer tipo que estenda Usuario e UsuarioEntity.

```
- Princípio da Segregação de Interfaces (ISP):
```
Explicação: Muitas interfaces específicas são melhores que uma interface geral.

Interfaces Específicas (UsuarioCreateInputPort, UsuarioUpdateInputPort, etc.): Cada interface define uma única
operação (create, update, delete, findById), permitindo que os clientes (como AbstractUsuarioController) dependam
apenas do necessário.

Adaptadores (UsuarioCreateAdapter, UsuarioDeleteAdapter, etc.): Cada adaptador implementa uma interface
específica (UsuarioCreateOutputPort, UsuarioDeleteOutputPort, etc.), garantindo dependências mínimas.

```
- Princípio da Inversão de Dependência (DIP):
```
Explicação: Dependa de abstrações, não de implementações concretas.

Injeção de Dependências: O uso de @RequiredArgsConstructor para injetar interfaces (UsuarioCreateInputPort,
UsuarioCreateOutputPort, AbstractUsuarioMapper, etc.) é uma boa prática. Controladores e serviços dependem de
abstrações.

Interfaces Genéricas: Interfaces como UsuarioCreateInputPort<T> e UsuarioCreateOutputPort<E> permitem que o código
de alto nível use tipos genéricos, enquanto implementações específicas fornecem os detalhes.

Mappers: A injeção de AbstractUsuarioMapper segue o DIP, dependendo de uma abstração.

```


## Collections para Teste

#### Link para a Collection do Postman

#### Descrição dos Testes Manuais


## Notas
