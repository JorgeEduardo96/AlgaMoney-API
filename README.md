# AlgaMoney API - Curso Fullstack AlgaWorks (Angular e Spring)
Curso Fullstack da AlgaWorks Angular e Spring

## Sobre
Projeto do curso Fullstack da AlgaWorks.

## Tecnologias
- Java: 8.0
- Spring Boot: 2.3.1
- ORM: Hibernate
- SGBD: MySQL

## Ferramentas de Desenvolvimento
- IDE: Spring Tool Suite (4.6.0.RELEASE)
- Versionamento: GIT (GitHub)

## Estrutura do Projeto
- com.example.algamoney.api.event: Pacote de para tratamento e capturas de eventos.
- com.example.algamoney.api.exceptionhandler: Pacote responsável pela captura e tratamento de exceções.
- com.example.algamoney.api.model: Pacote das entidades do projeto.
- com.example.algamoney.api.repository: Pacote responsável pelos repositórios do projeto.
- com.example.algamoney.api.resource: Pacote dos controladores do projeto (Responsável pelo encaminhamento das requisições HTTP).
- com.example.algamoney.api.service: Pacote de serviços do projeto (Responsável pelas regras de negócio).

## Dependências
O projeto utiliza o Maven para gerenciar as depedências.
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- spring-boot-devtools
- spring-boot-starter-test
- mysql-connector-java
- lombok
- flyway-core
- spring-boot-starter-validation
- hibernate-java8
- jackson-datatype-jsr310
- commons-lang3
- hibernate-jpamodelgen
- spring-boot-starter-security
- spring-security-oauth2
- spring-security-jwt
