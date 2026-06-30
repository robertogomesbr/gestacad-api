
# Gestacad - API

Projeto desenvolvido no IFPE com objetivo de gerenciar salas e laboratórios, e repor aulas.




## Pré-requisitos para o ambiente:

- [Instalar o JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)

- [Instalar o Git](https://git-scm.com/install/)

- [Instalar o Docker / Docker-compose](https://docs.docker.com/get-started/get-docker/)

## Para rodar o projeto siga os passos abaixo  e execute os comandos:

1. Baixe o projeto do repositório git, exemplo:
```bash
git clone https://github.com/robertogomesbr/gestacad-api.git
```

2. Entre na pasta do projeto e execute o comando abaixo para levantar o banco de dados:

```bash
docker-compose up -d
```

3. Ainda na pasta do projeto, execute o comando abaixo para rodar o projeto:
```bash
./mvnw spring-boot:run
```
## Authors

- [@robertogomesbr](https://github.com/robertogomesbr)
- [@rhuangomes10](https://github.com/rhuangomes10)
- [@Jessicajeje](https://github.com/Jessicajeje)

