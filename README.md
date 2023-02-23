# API RESTful Controle de Pauta e Sessão

# Descrição

Projeto segue arquitetura Layers, possuí padrão de monitoramento Health Check, utiliza padrão RESTful para a API,
a aplicação possuí integração com o HEROKU, para a verificar se o CPF pode votar.

A aplicação utiliza PostgreSQL como base de dados.

# Requisitos

- JAVA 17
- MAVEN
- DOCKER
- POSTEGRESQL


## Instalação

Para Instalação e execução do projeto, siga os seguintes passos.


```bash
  mvn clean install

  docker build -t sicredi-cooperativismo-api-docker.jar .

  cd docker

  docker-compose up -d
```

## Documentação da API


| URL                                                     | Metodo       | Descrição                           |
|:--------------------------------------------------------| :--------- | :---------------------------------- |
| `http://localhost:8080/sicredi/swagger-ui/index.html#/` | `GET` | URL para Documentação da API |
| `http://localhost:8080/sicredi/actuator/health`       | `GET` | URL para monitoramento do status da API |
